package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.mixin.player.PlayerInventoryAccessor;
import com.skd.dinamyccombat.network.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin implements ClientPlayerAttackProperties {

    @Unique
    private int dinamyc_combat$clientComboCount = 0;

    @Unique
    private int dinamyc_combat$clientLastAttackTime = 0;

    @Unique
    private int dinamyc_combat$clientComboTimeout = 0;

    @Unique
    private boolean dinamyc_combat$isAttackKeyHeld = false;

    @Unique
    private boolean dinamyc_combat$animationActive = false;

    @Unique
    private int dinamyc_combat$animEndTick = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void dinamyc_combat$onClientTick(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (dinamyc_combat$clientComboTimeout > 0) {
            dinamyc_combat$clientComboTimeout--;
            if (dinamyc_combat$clientComboTimeout == 0) {
                dinamyc_combat$clientComboCount = 0;
            }
        }

        if (dinamyc_combat$animationActive && self.tickCount >= dinamyc_combat$animEndTick) {
            dinamyc_combat$animationActive = false;
        }

        if (dinamyc_combat$isAttackKeyHeld && ClientConfig.IS_HOLD_TO_ATTACK_ENABLED.get()) {
            float cooldown = self.getAttackStrengthScale(0.5F);
            if (cooldown >= 0.9F && !dinamyc_combat$animationActive) {
                var mainStack = self.getMainHandItem();
                var attributes = WeaponRegistry.getAttributes(mainStack);
                if (attributes != null) {
                    int comboCount = incrementAndGetComboCount(self.tickCount);
                    int cursorTarget = -1;
                    int[] entityIds = new int[0];
                    EntityHitResult hit = pickEntityTarget(self);
                    if (hit != null && hit.getEntity() != null) {
                        cursorTarget = hit.getEntity().getId();
                        entityIds = new int[]{cursorTarget};
                    }
                    var packet = new Packets.C2S_AttackRequest(comboCount, self.isShiftKeyDown(),
                            ((PlayerInventoryAccessor) self.getInventory()).getSelected(), cursorTarget, entityIds);
                    ClientPacketDistributor.sendToServer(packet);
                    self.swing(InteractionHand.MAIN_HAND);

                    float animDuration = getAnimDuration(attributes, comboCount);
                    dinamyc_combat$animEndTick = self.tickCount + (int)(animDuration * 20);
                    dinamyc_combat$animationActive = true;
                }
            }
        }
    }

    @Unique
    private static float getAnimDuration(com.skd.dinamyccombat.api.WeaponAttributes attributes, int comboCount) {
        var attacks = attributes.attacks();
        if (attacks != null && attacks.length > 0) {
            int index = Math.abs(comboCount) % attacks.length;
            return (float)(0.3 + attacks[index].upswing());
        }
        return 0.5F;
    }

    @Override
    public int getClientComboCount() {
        return dinamyc_combat$clientComboCount;
    }

    @Override
    public void setClientComboCount(int count) {
        dinamyc_combat$clientComboCount = count;
    }

    @Override
    public int incrementAndGetComboCount(int tickCount) {
        int currentTime = tickCount;
        if (currentTime - dinamyc_combat$clientLastAttackTime < 40) {
            dinamyc_combat$clientComboCount++;
        } else {
            dinamyc_combat$clientComboCount = 1;
        }
        dinamyc_combat$clientLastAttackTime = currentTime;
        dinamyc_combat$clientComboTimeout = 40;
        return dinamyc_combat$clientComboCount;
    }

    @Override
    public boolean isClientAttackKeyHeld() {
        return dinamyc_combat$isAttackKeyHeld;
    }

    @Override
    public void setClientAttackKeyHeld(boolean held) {
        dinamyc_combat$isAttackKeyHeld = held;
    }

    @Override
    public boolean isAnimationActive() {
        return dinamyc_combat$animationActive;
    }

    @Override
    public void setAnimationActive(boolean active, int endTick) {
        dinamyc_combat$animationActive = active;
        dinamyc_combat$animEndTick = endTick;
    }

    @Unique
    private static EntityHitResult pickEntityTarget(LocalPlayer player) {
        var hit = Minecraft.getInstance().hitResult;
        if (hit != null && hit.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
            return (EntityHitResult) hit;
        }
        return null;
    }
}
