package com.skd.dinamyccombat.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private boolean isDestroying;

    @Inject(method = "attack", at = @At("HEAD"))
    private void dinamyc_combat$onAttackEntity(Player player, Entity target, CallbackInfo ci) {
        if (minecraft.player != null && minecraft.player.isAlive()) {
        }
    }

    @Inject(method = "startDestroyBlock", at = @At("HEAD"))
    private void dinamyc_combat$onStartDestroyBlock(CallbackInfoReturnable<Boolean> cir) {
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"))
    private void dinamyc_combat$onContinueDestroyBlock(CallbackInfoReturnable<Boolean> cir) {
    }

    @Inject(method = "stopDestroyBlock", at = @At("HEAD"))
    private void dinamyc_combat$onStopDestroyBlock(CallbackInfo ci) {
    }
}
