package com.skd.dinamyccombat.utils;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.api.WeaponAttributes;
import com.skd.dinamyccombat.network.Packets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Random;

public class SoundHelper {
    private static final Random rng = new Random();

    public static void playSound(ServerLevel world, Entity entity, WeaponAttributes.Sound sound) {
        if (sound == null) return;
        try {
            float pitch = (sound.randomness() > 0)
                    ? rng.nextFloat(sound.pitch() - sound.randomness(), sound.pitch() + sound.randomness())
                    : sound.pitch();
            var packet = new Packets.AttackSound(
                    entity.getX(), entity.getY(), entity.getZ(),
                    sound.id(), sound.volume(), pitch, rng.nextLong());
            var origin = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            var distance = 16.0;
            for (var serverPlayer : world.players()) {
                if (serverPlayer.position().distanceTo(origin) <= distance) {
                    PacketDistributor.sendToPlayer(serverPlayer, packet);
                }
            }
        } catch (Exception e) {
            DinamyCombat.LOGGER.error("Failed to play sound: " + sound.id(), e);
        }
    }

    public static List<String> soundKeys = List.of(
            "anchor_slam", "axe_slash", "claymore_swing", "claymore_stab", "claymore_slam",
            "dagger_slash", "double_axe_swing", "fist_punch", "glaive_slash_quick", "glaive_slash_slow",
            "hammer_slam", "katana_slash", "mace_slam", "mace_slash", "pickaxe_swing",
            "rapier_slash", "rapier_stab", "scythe_slash", "spear_stab",
            "staff_slam", "staff_slash", "staff_spin", "staff_stab",
            "sickle_slash", "sword_slash", "wand_swing"
    );
}
