package com.skd.dinamyccombat.client.effect;

import com.skd.dinamyccombat.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class WeaponEffectManager {
    private static final Map<UUID, ActiveEffect> ACTIVE = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static void startAttack(Player player, String presetId) {
        if (!ClientConfig.IS_SHOWING_WEAPON_TRAILS.get() && !ClientConfig.IS_SHOWING_SLASH_EFFECTS.get()) return;
        ACTIVE.put(player.getUUID(), new ActiveEffect(presetId, System.currentTimeMillis()));
    }

    public static void clientTick() {
        if (ACTIVE.isEmpty()) return;
        var mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        long now = System.currentTimeMillis();
        var iter = ACTIVE.entrySet().iterator();
        while (iter.hasNext()) {
            var entry = iter.next();
            ActiveEffect effect = entry.getValue();
            float elapsed = (now - effect.startTime) / 1000f;
            if (elapsed > 0.5f) { iter.remove(); continue; }

            float progress = elapsed / 0.5f;
            Player player = mc.level.getPlayerByUUID(entry.getKey());
            if (player == null) { iter.remove(); continue; }

            Vec3 localPos = effect.getSwingPosition(progress);

            Vec3 look = player.getLookAngle();
            Vec3 right = look.cross(player.getUpVector(1.0F)).normalize();
            Vec3 offset = right.scale(localPos.x)
                    .add(player.getUpVector(1.0F).scale(localPos.y))
                    .add(look.scale(localPos.z));
            Vec3 worldPos = player.getEyePosition().add(look.scale(1.2)).add(offset);

            if (ClientConfig.IS_SHOWING_WEAPON_TRAILS.get()) {
                spawnTrailParticles(mc, worldPos, progress);
            }
            if (ClientConfig.IS_SHOWING_SLASH_EFFECTS.get()) {
                spawnSlashParticles(mc, worldPos, effect, progress);
            }
        }
    }

    private static void spawnTrailParticles(Minecraft mc, Vec3 pos, float progress) {
        if (progress < 0.05f || progress > 0.95f) return;
        if (RANDOM.nextFloat() >= 0.5f) return;
        mc.level.addParticle(
                ParticleTypes.SWEEP_ATTACK,
                pos.x, pos.y, pos.z,
                (RANDOM.nextDouble() - 0.5) * 0.1,
                (RANDOM.nextDouble() - 0.5) * 0.1,
                (RANDOM.nextDouble() - 0.5) * 0.1
        );
    }

    private static void spawnSlashParticles(Minecraft mc, Vec3 pos, ActiveEffect effect, float progress) {
        int count = (int)(3 + progress * 5);
        for (int i = 0; i < count; i++) {
            float spread = 0.3f;
            mc.level.addParticle(
                    ParticleTypes.END_ROD,
                    pos.x + (RANDOM.nextDouble() - 0.5) * spread,
                    pos.y + (RANDOM.nextDouble() - 0.5) * spread,
                    pos.z + (RANDOM.nextDouble() - 0.5) * spread,
                    (RANDOM.nextDouble() - 0.5) * 0.3,
                    (RANDOM.nextDouble() - 0.5) * 0.3,
                    (RANDOM.nextDouble() - 0.5) * 0.3
            );
        }
    }

    private static class ActiveEffect {
        final SwingArc arc;
        final long startTime;

        ActiveEffect(String presetId, long startTime) {
            this.arc = SwingArc.fromPreset(presetId);
            this.startTime = startTime;
        }

        Vec3 getSwingPosition(float progress) {
            float angle = arc.getAngle(Mth.clamp(progress, 0, 1));
            if (arc.horizontal) {
                return new Vec3(arc.radius * Mth.sin(angle), 0, arc.radius * Mth.cos(angle) - arc.radius);
            } else {
                return new Vec3(0, arc.radius * Mth.sin(angle), arc.radius * Mth.cos(angle) - arc.radius);
            }
        }
    }
}
