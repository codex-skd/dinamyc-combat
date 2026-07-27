package com.skd.dinamyccombat.neoforge.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.skd.dinamyccombat.logic.PlayerAttachments;

import java.util.function.Supplier;

public class DinamycCombatPlayerAttachments {
    private static Supplier<AttachmentType<String>> MAIN_HAND_IDLE_ANIM;
    private static Supplier<AttachmentType<String>> OFF_HAND_IDLE_ANIM;

    public static void init(DeferredRegister<AttachmentType<?>> register) {
        MAIN_HAND_IDLE_ANIM = register.register(
                PlayerAttachments.MAIN_HAND_IDLE_ANIM.getPath(),
                () -> AttachmentType.builder(() -> "").serialize(Codec.STRING.fieldOf("value")).build());
        OFF_HAND_IDLE_ANIM = register.register(
                PlayerAttachments.OFF_HAND_IDLE_ANIM.getPath(),
                () -> AttachmentType.builder(() -> "").serialize(Codec.STRING.fieldOf("value")).build());
    }

    public static String getMainHandIdleAnimation(Player player) {
        return player.getData(MAIN_HAND_IDLE_ANIM.get());
    }

    public static String getOffHandIdleAnimation(Player player) {
        return player.getData(OFF_HAND_IDLE_ANIM.get());
    }

    public static void setMainHandIdleAnimation(Player player, String animation) {
        player.setData(MAIN_HAND_IDLE_ANIM.get(), animation);
    }

    public static void setOffHandIdleAnimation(Player player, String animation) {
        player.setData(OFF_HAND_IDLE_ANIM.get(), animation);
    }
}
