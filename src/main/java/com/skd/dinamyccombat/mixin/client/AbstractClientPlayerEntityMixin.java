package com.skd.dinamyccombat.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Unique
    private float dinamyc_combat$prevAttackProgress = 0F;

    @Unique
    private float dinamyc_combat$attackProgress = 0F;

    @Unique
    private int dinamyc_combat$attackCooldownTicks = 0;

    @Inject(method = "getFieldOfViewModifier", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$getFovModifier(CallbackInfoReturnable<Float> cir) {
    }

    @Unique
    public float dinamyc_combat$getAttackProgress(float tickDelta) {
        if (dinamyc_combat$attackCooldownTicks <= 0) {
            return 0F;
        }
        return Math.min(1.0F, (dinamyc_combat$prevAttackProgress + (dinamyc_combat$attackProgress - dinamyc_combat$prevAttackProgress) * tickDelta));
    }

    @Unique
    public void dinamyc_combat$resetAttackCooldown(int ticks) {
        dinamyc_combat$attackCooldownTicks = ticks;
        dinamyc_combat$attackProgress = 0F;
        dinamyc_combat$prevAttackProgress = 0F;
    }

    @Unique
    public int dinamyc_combat$getAttackCooldownTicks() {
        return dinamyc_combat$attackCooldownTicks;
    }
}
