package com.skd.dinamyccombat.mixin.player;

import com.skd.dinamyccombat.config.ServerConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private float dinamyc_combat$originalKnockback;

    @ModifyVariable(method = "knockback", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double dinamyc_combat$modifyKnockback(double knockback) {
        if (!ServerConfig.KNOCKBACK_REDUCED_FOR_FAST_ATTACKS.get()) {
            return knockback;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof Player player) {
            float cooldown = player.getAttackStrengthScale(0.5F);
            if (cooldown < ServerConfig.KNOCKBACK_REDUCTION_THRESHOLD.get()) {
                double factor;
                switch (ServerConfig.KNOCKBACK_REDUCTION_CURVE.get()) {
                    case SQUARE:
                        factor = cooldown * cooldown;
                        break;
                    case HALF_SQUARE:
                        factor = Math.sqrt(cooldown);
                        break;
                    default:
                        factor = cooldown;
                        break;
                }
                double threshold = ServerConfig.KNOCKBACK_REDUCTION_THRESHOLD.get();
                factor = factor / threshold;
                return knockback * Math.max(0.0, Math.min(1.0, factor));
            }
        }
        return knockback;
    }

    @Inject(method = "isBlocking", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$disableBlockingForTwoHanded(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof Player) {
            ItemStack mainHand = self.getItemInHand(InteractionHand.MAIN_HAND);
            if (isTwoHandedWeapon(mainHand)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "isEffectiveAi", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$disableOffhandForTwoHanded(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof Player) {
            ItemStack mainHand = self.getItemInHand(InteractionHand.MAIN_HAND);
            if (isTwoHandedWeapon(mainHand)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private static boolean isTwoHandedWeapon(ItemStack stack) {
        return false;
    }
}
