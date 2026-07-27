package com.skd.dinamyccombat.logic;

import com.skd.dinamyccombat.DinamycCombat;
import com.skd.dinamyccombat.api.AttackHand;
import com.skd.dinamyccombat.api.ComboState;
import com.skd.dinamyccombat.api.WeaponAttributes;
import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.mixin.player.PlayerInventoryAccessor;
import com.skd.dinamyccombat.utils.AttributeModifierHelper;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PlayerAttackHelper {
    public static float getDualWieldingAttackDamageMultiplier(Player player, AttackHand hand) {
        return isDualWielding(player)
                ? (hand.isOffHand()
                    ? ServerConfig.DUAL_WIELDING_OFF_HAND_DAMAGE_MULTIPLIER.get().floatValue()
                    : ServerConfig.DUAL_WIELDING_MAIN_HAND_DAMAGE_MULTIPLIER.get().floatValue())
                : 1;
    }

    public static boolean shouldAttackWithOffHand(Player player, int comboCount) {
        return PlayerAttackHelper.isDualWielding(player) && comboCount % 2 == 1;
    }

    public static boolean isDualWielding(Player player) {
        var mainAttributes = WeaponRegistry.getAttributes(player.getMainHandItem());
        var offAttributes = WeaponRegistry.getAttributes(player.getOffhandItem());
        return isDualWielding(mainAttributes, offAttributes);
    }

    public static boolean isDualWielding(WeaponAttributes mainAttributes, WeaponAttributes offAttributes) {
        return mainAttributes != null && !mainAttributes.isTwoHanded()
                && offAttributes != null && !offAttributes.isTwoHanded();
    }

    public static boolean isTwoHandedWielding(Player player) {
        var mainAttributes = WeaponRegistry.getAttributes(player.getMainHandItem());
        if (mainAttributes != null) return mainAttributes.isTwoHanded();
        return false;
    }

    public static float getAttackCooldownTicksCapped(Player player) {
        return Math.max(player.getCurrentItemAttackStrengthDelay(), ServerConfig.ATTACK_INTERVAL_CAP.get());
    }

    @Nullable
    public static AttackHand getCurrentAttack(Player player, int comboCount) {
        if (isDualWielding(player)) {
            boolean isOffHand = shouldAttackWithOffHand(player, comboCount);
            var itemStack = isOffHand ? player.getOffhandItem() : player.getMainHandItem();
            var attributes = WeaponRegistry.getAttributes(itemStack);
            if (attributes != null && attributes.attacks() != null) {
                int handCombo = ((isOffHand && comboCount > 0) ? (comboCount - 1) : comboCount) / 2;
                var selection = selectAttack(handCombo, attributes, player, isOffHand);
                if (selection == null) return null;
                return new AttackHand(selection.attack, selection.combo, isOffHand, attributes, itemStack);
            }
        } else {
            var itemStack = player.getMainHandItem();
            var attributes = WeaponRegistry.getAttributes(itemStack);
            if (attributes != null && attributes.attacks() != null) {
                var selection = selectAttack(comboCount, attributes, player, false);
                if (selection == null) return null;
                return new AttackHand(selection.attack, selection.combo, false, attributes, itemStack);
            }
        }
        return null;
    }

    private record AttackSelection(WeaponAttributes.Attack attack, ComboState combo) {}

    @Nullable
    private static AttackSelection selectAttack(int comboCount, WeaponAttributes attributes, Player player, boolean isOffHand) {
        var attacks = attributes.attacks();
        attacks = Arrays.stream(attacks)
                .filter(attack -> attack.conditions() == null
                        || attack.conditions().length == 0
                        || evaluateConditions(attack.conditions(), player, isOffHand))
                .toArray(WeaponAttributes.Attack[]::new);
        if (comboCount < 0) comboCount = 0;
        if (attacks.length == 0) return null;
        int index = comboCount % attacks.length;
        return new AttackSelection(attacks[index], new ComboState(index + 1, attacks.length));
    }

    private static boolean evaluateConditions(WeaponAttributes.Condition[] conditions, Player player, boolean isOffHand) {
        return Arrays.stream(conditions).allMatch(c -> evaluateCondition(c, player, isOffHand));
    }

    private static boolean evaluateCondition(WeaponAttributes.Condition condition, Player player, boolean isOffHand) {
        if (condition == null) return true;
        switch (condition) {
            case NOT_DUAL_WIELDING -> { return !isDualWielding(player); }
            case DUAL_WIELDING_ANY -> { return isDualWielding(player); }
            case DUAL_WIELDING_SAME -> {
                return isDualWielding(player)
                        && (player.getMainHandItem().getItem() == player.getOffhandItem().getItem());
            }
            case DUAL_WIELDING_SAME_CATEGORY -> {
                if (!isDualWielding(player)) return false;
                var mh = WeaponRegistry.getAttributes(player.getMainHandItem());
                var oh = WeaponRegistry.getAttributes(player.getOffhandItem());
                if (mh == null || oh == null || mh.category() == null || oh.category() == null) return false;
                return mh.category().equals(oh.category());
            }
            case NO_OFFHAND_ITEM -> {
                var offhand = player.getOffhandItem();
                if (offhand == null || offhand.isEmpty()) return true;
                return false;
            }
            case OFF_HAND_SHIELD -> {
                return player.getOffhandItem().getItem() instanceof ShieldItem;
            }
            case MAIN_HAND_ONLY -> { return !isOffHand; }
            case OFF_HAND_ONLY -> { return isOffHand; }
            case MOUNTED -> { return player.getVehicle() != null; }
            case NOT_MOUNTED -> { return player.getVehicle() == null; }
        }
        return true;
    }

    public static void swapHandAttributes(Player player, boolean useOffHand, Runnable runnable) {
        if (!useOffHand) { runnable.run(); return; }
        synchronized (player) {
            var inventory = player.getInventory();
            var mainStack = player.getMainHandItem();
            var offStack = InventoryUtil.getOffHandSlotStack(player);
            setAttributesForOffHandAttack(player, true);
            var accessor = (PlayerInventoryAccessor) inventory;
            inventory.setSelectedSlot(accessor.getSelected());
            InventoryUtil.setOffHandSlotStack(player, mainStack);
            runnable.run();
            inventory.setSelectedSlot(accessor.getSelected());
            InventoryUtil.setOffHandSlotStack(player, offStack);
            setAttributesForOffHandAttack(player, false);
        }
    }

    private static void setAttributesForOffHandAttack(Player player, boolean useOffHand) {
        var mainStack = player.getMainHandItem();
        var offStack = player.getOffhandItem();
        ItemStack add, remove;
        if (useOffHand) { remove = mainStack; add = offStack; }
        else { remove = offStack; add = mainStack; }
        if (remove != null) {
            var modMap = AttributeModifierHelper.modifierMultimap(remove);
            modMap.forEach((attribute, modifier) -> {
                var instance = player.getAttributes().getInstance(attribute);
                if (instance != null) {
                    instance.removeModifier(modifier);
                }
            });
        }
        if (add != null) {
            var modMap = AttributeModifierHelper.modifierMultimap(add);
            player.getAttributes().addTransientAttributeModifiers(modMap);
        }
    }

    public static Pose poseForPlayer(Player player) {
        var mainAttributes = WeaponRegistry.getAttributes(player.getMainHandItem());
        String mainPose = (mainAttributes != null && mainAttributes.pose() != null) ? mainAttributes.pose() : "";
        var offAttributes = WeaponRegistry.getAttributes(player.getOffhandItem());
        String offPose = (isDualWielding(mainAttributes, offAttributes)
                && offAttributes != null && offAttributes.pose() != null) ? offAttributes.pose() : "";
        return new Pose(mainPose, offPose);
    }

    public static double getStaticRange(Player player, ItemStack stack) {
        var attributes = WeaponRegistry.getAttributes(stack);
        return combineAttackRange(attributes, player.getAttributeBaseValue(Attributes.ENTITY_INTERACTION_RANGE));
    }

    public static double getRangeForItem(Player player, ItemStack stack) {
        return getRangeWithItem(stack, player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE));
    }

    public static double getRangeWithWeapon(Player player, double interactionRange) {
        return getRangeWithItem(player.getMainHandItem(), interactionRange);
    }

    private static double getRangeWithItem(ItemStack stack, double interactionRange) {
        if (EntityAttributeHelper.itemHasRangeAttribute(stack)) return interactionRange;
        var attributes = WeaponRegistry.getAttributes(stack);
        return combineAttackRange(attributes, interactionRange);
    }

    public static double combineAttackRange(WeaponAttributes attributes, double interactionRange) {
        var range = interactionRange;
        if (attributes != null) {
            if (attributes.attackRange() != 0) return attributes.attackRange();
            range += attributes.rangeBonus();
        }
        return range;
    }
}
