package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftClientInject {

    @Shadow
    public LocalPlayer player;

    @Shadow
    public HitResult hitResult;

    @Shadow
    private int rightClickDelay;

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$onStartAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player == null) return;
        if (!ServerConfig.ALLOW_FAST_ATTACKS.get()) return;
        if (rightClickDelay > 0) return;

        if (player instanceof ClientPlayerAttackProperties cprops) {
            cprops.setClientAttackKeyHeld(true);
            cprops.markClickAttack();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void dinamyc_combat$onTick(CallbackInfo ci) {
    }

    @Inject(method = "continueAttack", at = @At("HEAD"))
    private void dinamyc_combat$onContinueAttack(boolean bl, CallbackInfo ci) {
        if (player == null) return;

        boolean attackPressed = Minecraft.getInstance().options.keyAttack.isDown();

        if (player instanceof ClientPlayerAttackProperties cprops) {
            cprops.setClientAttackKeyHeld(attackPressed);
        }
    }
}
