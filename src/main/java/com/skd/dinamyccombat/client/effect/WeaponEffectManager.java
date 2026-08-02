package com.skd.dinamyccombat.client.effect;

import com.skd.dinamyccombat.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class WeaponEffectManager {
    private static final Map<UUID, ActiveEffect> ACTIVE = new HashMap<>();

    public static void startAttack(Player player, String presetId) {
        ACTIVE.put(player.getUUID(), new ActiveEffect(System.currentTimeMillis()));
    }

    public static void clientTick() {
        if (ACTIVE.isEmpty() || !ClientConfig.IS_SHOWING_SWEEP_EFFECT.get()) return;
        var mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        long now = System.currentTimeMillis();
        var iter = ACTIVE.entrySet().iterator();
        while (iter.hasNext()) {
            var entry = iter.next();
            ActiveEffect effect = entry.getValue();
            float elapsed = (now - effect.startTime) / 1000f;
            if (elapsed > 0.3f) { iter.remove(); continue; }

            Player player = mc.level.getPlayerByUUID(entry.getKey());
            if (player == null) { iter.remove(); continue; }

            if (elapsed > 0.05f && elapsed < 0.15f) {
                Vec3 look = player.getLookAngle();
                Vec3 pos = player.getEyePosition().add(look.scale(1.5));
                mc.level.addParticle(
                        ParticleTypes.SWEEP_ATTACK,
                        pos.x, pos.y, pos.z,
                        0, 0, 0
                );
            }
        }
    }

    private record ActiveEffect(long startTime) {}
}
