package com.skd.dinamyccombat.client;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.logic.AnimatedHand;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.network.Packets;
import com.zigythebird.playeranim.accessors.IAnimatedAvatar;
import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.layered.modifier.MirrorIfLeftHandModifier;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;

public class ClientNetwork {

    private static final FirstPersonConfiguration FP_CONFIG =
            new FirstPersonConfiguration(true, true, true, true, true);

    public static void handleWeaponRegistrySync(Packets.WeaponRegistrySync packet) {
        WeaponRegistry.decodeRegistry(packet);
    }

    public static void handleConfigSync(Packets.ConfigSync packet) {
        DinamyCombat.LOGGER.info("Received config sync packet");
    }

    public static void handleAttackAnimation(Packets.AttackAnimation packet) {
        var client = Minecraft.getInstance();
        client.execute(() -> {
            if (client.level == null) return;
            var entity = client.level.getEntity(packet.playerId());
            if (entity == null) return;

            if (entity instanceof IAnimatedAvatar avatar) {
                var manager = avatar.playerAnimLib$getAnimManager();
                if (manager == null) return;

                if (packet.animationName().equals("!STOP!") || packet.animationName().equals("stop")) {
                    for (var pair : manager.getLayers()) {
                        if (pair.second() instanceof PlayerAnimationController controller) {
                            controller.stopTriggeredAnimation();
                            break;
                        }
                    }
                } else {
                    Identifier animId = Identifier.tryParse(packet.animationName());
                    if (animId == null) return;
                    var animation = PlayerAnimResources.getAnimation(animId);
                    if (animation == null) return;

                    for (var pair : manager.getLayers()) {
                        if (pair.second() instanceof PlayerAnimationController controller) {
                            MirrorIfLeftHandModifier mirrorMod = null;
                            if (entity == client.player) {
                                setupFirstPerson(controller);
                                if (packet.animatedHand() == AnimatedHand.OFF_HAND) {
                                    mirrorMod = new MirrorIfLeftHandModifier();
                                    controller.addModifierBefore(mirrorMod);
                                }
                            }
                            controller.triggerAnimation(animation, (float) packet.upswing());
                            break;
                        }
                    }
                }
            }
        });
    }

    private static void setupFirstPerson(PlayerAnimationController controller) {
        var configMode = ClientConfig.FIRST_PERSON_ANIMATIONS.get();
        if (configMode == com.skd.dinamyccombat.config.TriStateAuto.NO) {
            controller.setFirstPersonMode(FirstPersonMode.NONE);
            return;
        }
        if (configMode == com.skd.dinamyccombat.config.TriStateAuto.YES
                || (configMode == com.skd.dinamyccombat.config.TriStateAuto.AUTO
                    && ClientConfig.IS_SHOWING_ARMS_IN_FIRST_PERSON.get())) {
            controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
            controller.setFirstPersonConfiguration(FP_CONFIG);
        }
    }

    public static void handleAttackSound(Packets.AttackSound packet) {
        var client = Minecraft.getInstance();
        client.execute(() -> {
            try {
                var soundEventRef = BuiltInRegistries.SOUND_EVENT.get(Identifier.parse(packet.soundId()));
                if (soundEventRef.isPresent() && client.level != null) {
                    var soundEvent = soundEventRef.get().value();
                    client.level.playLocalSound(
                            packet.x(), packet.y(), packet.z(),
                            soundEvent, SoundSource.PLAYERS,
                            packet.volume(), packet.pitch(), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
