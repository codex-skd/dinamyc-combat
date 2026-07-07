package com.skd.dinamyccombat.mixin.player;

import com.skd.dinamyccombat.logic.InventoryUtil;
import com.skd.dinamyccombat.logic.PlayerAttackHelper;
import com.skd.dinamyccombat.logic.PlayerAttackProperties;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.logic.knockback.ConfigurableKnockback;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements ConfigurableKnockback {

    @Unique
    private float dinamyc_combat$customKnockbackMultiplier = 1F;

    @Override
    public void setKnockbackMultiplier_BetterCombat(float value) {
        dinamyc_combat$customKnockbackMultiplier = value;
    }

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public double dinamyc_combat$takeKnockback(double knockbackStrength) {
        return knockbackStrength * dinamyc_combat$customKnockbackMultiplier;
    }

    @Inject(method = "getItemBySlot", at = @At("HEAD"), cancellable = true, require = 0)
    public void dinamyc_combat$getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if (((Object) this) instanceof Player player) {
            var mainStack = player.getMainHandItem();
            var mainAttributes = WeaponRegistry.getAttributes(mainStack);
            boolean mainTwoHanded = mainAttributes != null && mainAttributes.isTwoHanded();

            var offStack = InventoryUtil.getOffHandSlotStack(player);
            var offAttributes = WeaponRegistry.getAttributes(offStack);
            boolean offTwoHanded = offAttributes != null && offAttributes.isTwoHanded();

            if (slot == EquipmentSlot.OFFHAND) {
                if (mainTwoHanded || offTwoHanded) {
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            }
        }
    }
}
