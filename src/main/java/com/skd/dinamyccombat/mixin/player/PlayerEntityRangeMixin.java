package com.skd.dinamyccombat.mixin.player;

import com.skd.dinamyccombat.config.ServerConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityRangeMixin {

    @Unique
    private static final double DEFAULT_ENTITY_REACH = 3.0;

    @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true)
    private static void dinamyc_combat$modifyCanHitEntity(Player player, Entity target,
            CallbackInfoReturnable<Boolean> cir) {
        ItemStack weapon = player.getItemInHand(InteractionHand.MAIN_HAND);
        double rangeBonus = getWeaponRangeBonus(weapon);
        double maxRange = DEFAULT_ENTITY_REACH + rangeBonus;
        double searchMultiplier = ServerConfig.TARGET_SEARCH_RANGE_MULTIPLIER.get();

        Vec3 eyePos = player.getEyePosition();
        double distSq = target.getBoundingBox().distanceToSqr(eyePos);
        double effectiveRange = maxRange * searchMultiplier;

        if (distSq > effectiveRange * effectiveRange) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getEntityInteractionRange", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$getEntityInteractionRange(CallbackInfoReturnable<Double> cir) {
        Player self = (Player) (Object) this;
        ItemStack weapon = self.getItemInHand(InteractionHand.MAIN_HAND);
        double rangeBonus = getWeaponRangeBonus(weapon);
        if (rangeBonus > 0) {
            cir.setReturnValue(DEFAULT_ENTITY_REACH + rangeBonus);
        }
    }

    @Unique
    private static double getWeaponRangeBonus(ItemStack stack) {
        return 0.0;
    }
}
