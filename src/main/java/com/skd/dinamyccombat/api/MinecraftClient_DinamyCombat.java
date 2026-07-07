package com.skd.dinamyccombat.api;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public interface MinecraftClient_DinamyCombat {
    int getComboCount();
    boolean hasTargetsInReach();

    @Nullable
    default Entity getCursorTarget() {
        var client = (Minecraft) this;
        if (client.hitResult != null && client.hitResult.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
            return ((EntityHitResult) client.hitResult).getEntity();
        }
        return null;
    }

    int getUpswingTicks();
    float getSwingProgress();

    default boolean isWeaponSwingInProgress() {
        return getSwingProgress() < 1F;
    }

    AttackHand getCurrentAttackHand();

    default WeaponAttributes.Attack getCurrentAttack() {
        var attackHand = getCurrentAttackHand();
        if (attackHand == null) return null;
        return attackHand.attack();
    }

    void cancelUpswing();
}
