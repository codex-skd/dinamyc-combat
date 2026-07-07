package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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

    @Shadow
    private int missTime;

    @Unique
    private boolean dinamyc_combat$attackKeyWasDown = false;

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$onStartAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player == null) {
            return;
        }
        if (!ServerConfig.ALLOW_FAST_ATTACKS.get()) {
            return;
        }
        if (rightClickDelay > 0) {
            return;
        }

        float cooldown = player.getAttackStrengthScale(0.5F);
        if (cooldown < 0.9F) {
            return;
        }

        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hitResult;
            Entity target = entityHit.getEntity();
            if (target != null && !player.isPassengerOfSameVehicle(target)) {
            }
        }

        missTime = 0;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void dinamyc_combat$onTick(CallbackInfo ci) {
        if (player != null) {
            float cooldown = player.getAttackStrengthScale(0.5F);
            if (cooldown >= 0.9F) {
                missTime = 0;
            }
        }
    }

    @Inject(method = "continueAttack", at = @At("HEAD"))
    private void dinamyc_combat$onContinueAttack(boolean bl, CallbackInfo ci) {
        if (player == null) {
            return;
        }

        boolean attackPressed = Minecraft.getInstance().options.keyAttack.isDown();
        dinamyc_combat$attackKeyWasDown = attackPressed;
    }

    @Unique
    private static Minecraft getInstance() {
        return Minecraft.getInstance();
    }
}
