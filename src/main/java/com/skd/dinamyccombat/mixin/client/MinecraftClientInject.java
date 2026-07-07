package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.mixin.player.PlayerInventoryAccessor;
import com.skd.dinamyccombat.network.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftClientInject {

    private static final Logger LOGGER = LogUtils.getLogger();

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
        if (player == null) return;
        if (!ServerConfig.ALLOW_FAST_ATTACKS.get()) return;
        if (rightClickDelay > 0) return;

        float cooldown = player.getAttackStrengthScale(0.5F);
        if (cooldown < 0.9F) return;

        var attrs = WeaponRegistry.getAttributes(player.getMainHandItem());
        if (attrs == null) attrs = WeaponRegistry.getAttributes(player.getOffhandItem());
        if (attrs == null) return;

        if (player instanceof ClientPlayerAttackProperties cprops && cprops.isAnimationActive()) {
            missTime = 0;
            cir.setReturnValue(false);
            return;
        }

        int comboCount = 0;
        if (player instanceof ClientPlayerAttackProperties cprops) {
            comboCount = cprops.incrementAndGetComboCount(player.tickCount);
            cprops.setAnimationActive(true, player.tickCount + getAnimDuration(attrs, comboCount));
            cprops.setComboState((Math.abs(comboCount) % attrs.attacks().length) + 1, attrs.attacks().length);
        }

        int cursorTarget = -1;
        int[] entityIds = new int[0];
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hitResult;
            Entity target = entityHit.getEntity();
            if (target != null && !player.isPassengerOfSameVehicle(target)) {
                cursorTarget = target.getId();
                entityIds = new int[]{target.getId()};
            }
        }

        var packet = new Packets.C2S_AttackRequest(comboCount, player.isShiftKeyDown(),
                ((PlayerInventoryAccessor) player.getInventory()).getSelected(), cursorTarget, entityIds);
        ClientPacketDistributor.sendToServer(packet);

        player.swing(InteractionHand.MAIN_HAND);
        missTime = 0;
        cir.setReturnValue(false);
    }

    @Unique
    private static int getAnimDuration(com.skd.dinamyccombat.api.WeaponAttributes attributes, int comboCount) {
        var attacks = attributes.attacks();
        if (attacks != null && attacks.length > 0) {
            int index = Math.abs(comboCount) % attacks.length;
            return (int)((0.3 + attacks[index].upswing()) * 20);
        }
        return 10;
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
        if (player == null) return;

        boolean attackPressed = Minecraft.getInstance().options.keyAttack.isDown();
        dinamyc_combat$attackKeyWasDown = attackPressed;

        if (player instanceof ClientPlayerAttackProperties cprops) {
            cprops.setClientAttackKeyHeld(attackPressed);
        }
    }
}
