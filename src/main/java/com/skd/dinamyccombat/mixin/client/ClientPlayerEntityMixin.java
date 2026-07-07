package com.skd.dinamyccombat.mixin.client;

import com.mojang.authlib.GameProfile;
import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
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

    @Inject(require = 0, method = "attack", at = @At("HEAD"))
    private void dinamyc_combat$onClientAttack(Entity target, CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        int currentTime = self.tickCount;
        if (currentTime - dinamyc_combat$clientLastAttackTime < 40) {
            dinamyc_combat$clientComboCount++;
        } else {
            dinamyc_combat$clientComboCount = 1;
        }
        dinamyc_combat$clientLastAttackTime = currentTime;
        dinamyc_combat$clientComboTimeout = 40;
    }

    @Inject(require = 0, method = "tick", at = @At("HEAD"))
    private void dinamyc_combat$onClientTick(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (dinamyc_combat$clientComboTimeout > 0) {
            dinamyc_combat$clientComboTimeout--;
            if (dinamyc_combat$clientComboTimeout == 0) {
                dinamyc_combat$clientComboCount = 0;
            }
        }

        if (dinamyc_combat$isAttackKeyHeld) {
            float cooldown = self.getAttackStrengthScale(0.5F);
            if (cooldown >= 0.9F) {
                EntityHitResult hit = pickEntityTarget(self);
                if (hit != null) {
                    self.attack(hit.getEntity());
                }
            }
        }
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

    @Unique
    private static EntityHitResult pickEntityTarget(LocalPlayer player) {
        var hit = Minecraft.getInstance().hitResult;
        if (hit != null && hit.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
            return (EntityHitResult) hit;
        }
        return null;
    }
}
