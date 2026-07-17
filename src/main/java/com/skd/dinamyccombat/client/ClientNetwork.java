package com.skd.dinamyccombat.client;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.config.TriStateAuto;
import com.skd.dinamyccombat.logic.AnimatedHand;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.network.Packets;
import com.skd.playeranimationcore.animation.AnimationData;
import com.skd.playeranimationcore.animation.AnimationController;
import com.skd.playeranimationcore.animation.PlayerAnimResources;
import com.skd.playeranimationcore.animation.PlayerAnimationController;
import com.skd.playeranimationcore.animation.layered.IAnimation;
import com.skd.playeranimationcore.animation.layered.modifier.MirrorIfLeftHandModifier;
import com.skd.playeranimationcore.animation.layered.modifier.SpeedModifier;
import com.skd.playeranimationcore.api.PlayerAnimationAccess;
import com.skd.playeranimationcore.api.PlayerAnimationFactory;
import com.skd.playeranimationcore.api.firstPerson.FirstPersonConfiguration;
import com.skd.playeranimationcore.api.firstPerson.FirstPersonMode;
import com.skd.playeranimationcore.enums.PlayState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ClientNetwork {

    public static final Identifier FACTORY_ID = Identifier.fromNamespaceAndPath(DinamyCombat.MODID, "combat");
    private static final FirstPersonConfiguration FP_CONFIG = new FirstPersonConfiguration()
            .setShowRightArm(true).setShowLeftArm(true)
            .setShowRightItem(true).setShowLeftItem(true)
            .setShowArmor(true);

    public static void init() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                FACTORY_ID, 1000, CombatAnimationController::new);
    }

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
            if (!(entity instanceof Avatar avatar)) return;

            var layer = PlayerAnimationAccess.getPlayerAnimationLayer(avatar, FACTORY_ID);
            if (!(layer instanceof PlayerAnimationController controller)) return;

            if (entity == client.player && packet.animatedHand() == AnimatedHand.OFF_HAND) {
                controller.addModifierBefore(new MirrorIfLeftHandModifier());
            }

            Identifier animId = Identifier.tryParse(packet.animationName());
            if (animId != null && PlayerAnimResources.hasAnimation(animId)) {
                // Add speed modifier based on weapon attack speed
                if (entity instanceof net.minecraft.world.entity.player.Player player) {
                    float speed = (float)player.getAttributeValue(Attributes.ATTACK_SPEED);
                    if (speed > 0 && Float.isFinite(speed)) {
                        controller.removeModifierIf(m -> m instanceof SpeedModifier);
                        controller.addModifierBefore(new SpeedModifier(speed));
                    }
                }
                controller.triggerAnimation(animId);
            }
        });
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

    public static class CombatAnimationController extends PlayerAnimationController {

        public CombatAnimationController(Avatar avatar) {
            super(avatar, CombatAnimationController::handleState);
            applyFirstPersonConfig(this);
        }

        private static PlayState handleState(
                AnimationController controller,
                AnimationData data,
                AnimationSetter setter) {
            return PlayState.CONTINUE;
        }

        @Override
        public FirstPersonMode getFirstPersonMode() {
            if (!isFirstPersonEnabled()) return FirstPersonMode.NONE;
            if (isAttackAnimationPlaying(this.avatar)) {
                // Respect client config: show arms or keep vanilla view
                if (ClientConfig.IS_SHOWING_ARMS_IN_FIRST_PERSON.get()) {
                    return FirstPersonMode.THIRD_PERSON_MODEL;
                }
            }
            return FirstPersonMode.NONE;
        }

        @Override
        public FirstPersonConfiguration getFirstPersonConfiguration() {
            FirstPersonConfiguration hardSet = super.getFirstPersonConfiguration();
            if (hardSet != IAnimation.DEFAULT_FIRST_PERSON_CONFIG) return hardSet;
            return FP_CONFIG;
        }
    }

    public static boolean isFirstPersonEnabled() {
        var configMode = ClientConfig.FIRST_PERSON_ANIMATIONS.get();
        return configMode == TriStateAuto.YES
                || (configMode == TriStateAuto.AUTO
                    && ClientConfig.IS_SHOWING_ARMS_IN_FIRST_PERSON.get());
    }

    public static boolean isAttackAnimationPlaying(Avatar avatar) {
        var layer = PlayerAnimationAccess.getPlayerAnimationLayer(avatar, FACTORY_ID);
        if (layer instanceof PlayerAnimationController controller) {
            return !controller.hasAnimationFinished();
        }
        return false;
    }

    private static void applyFirstPersonConfig(PlayerAnimationController controller) {
        if (isFirstPersonEnabled()) {
            controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
            controller.setFirstPersonConfiguration(FP_CONFIG);
        } else {
            controller.setFirstPersonMode(FirstPersonMode.NONE);
        }
    }
}
