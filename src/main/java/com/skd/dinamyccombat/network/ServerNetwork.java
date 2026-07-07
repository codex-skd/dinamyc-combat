package com.skd.dinamyccombat.network;

import com.mojang.logging.LogUtils;
import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.api.WeaponAttributes;
import com.skd.dinamyccombat.logic.AnimatedHand;
import com.skd.dinamyccombat.logic.PlayerAttackHelper;
import com.skd.dinamyccombat.logic.PlayerAttackProperties;
import com.skd.dinamyccombat.utils.SoundHelper;
import com.skd.dinamyccombat.mixin.player.LivingEntityAccessor;
import com.skd.dinamyccombat.config.ServerConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

public class ServerNetwork {
    static final Logger LOGGER = LogUtils.getLogger();

    public static void handleAttackAnimation(Packets.AttackAnimation packet, MinecraftServer server, ServerPlayer player) {
        ServerLevel world = (ServerLevel) player.level();
        final var forwardPacket = new Packets.AttackAnimation(
                player.getId(), packet.animatedHand(), packet.animationName(),
                packet.length(), packet.upswing(), packet.weaponRange(),
                packet.upswingTicks(), packet.particles());
        for (var serverPlayer : world.players()) {
            PacketDistributor.sendToPlayer(serverPlayer, forwardPacket);
        }
    }

    public static void handleAttackRequest(Packets.C2S_AttackRequest request, MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler) {
        ServerLevel world = (ServerLevel) player.level();
        final var hand = PlayerAttackHelper.getCurrentAttack(player, request.comboCount());
        if (hand == null) return;
        final var attack = hand.attack();
        final var attributes = hand.attributes();
        world.getServer().execute(() -> {
            ((PlayerAttackProperties) player).setComboCount(request.comboCount());
            PlayerAttackHelper.swapHandAttributes(player, hand.isOffHand(), () -> {
                if (attributes != null && attack != null) {
                    SoundHelper.playSound(world, player, attack.swingSound());

                    String animName = attack.animation();
                    if (animName != null && !animName.isEmpty()) {
                        if (hand.isOffHand()) {
                            animName = animName.replace("_right", "_left");
                        }
                        var animHand = hand.isOffHand() ? AnimatedHand.OFF_HAND : AnimatedHand.MAIN_HAND;
                        float length = 0.5F;
                        float upswing = (float) attack.upswing();
                        float weaponRange = 1.0F;
                        if (attributes.attackRange() != 0) {
                            weaponRange = (float) attributes.attackRange();
                        } else {
                            weaponRange = (float) (player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE) + attributes.rangeBonus());
                        }
                        var animPacket = new Packets.AttackAnimation(
                                player.getId(), animHand, animName,
                                length, upswing, weaponRange, 0, Packets.SwingParticles.EMPTY);
                        for (var serverPlayer : world.players()) {
                            PacketDistributor.sendToPlayer(serverPlayer, animPacket);
                        }
                    }
                }
                var lastAttackedTicks = ((LivingEntityAccessor) player).betterCombat_getTicksSinceLastAttack();
                boolean attackedAny = false;
                for (int entityId : request.entityIds()) {
                    var entity = world.getEntity(entityId);
                    if (entity == null || entity == player.getVehicle()) continue;
                    if (entity instanceof ArmorStand armorStand && armorStand.isMarker()) continue;
                    ((LivingEntityAccessor) player).betterCombat_setTicksSinceLastAttack((int) lastAttackedTicks);
                    if (entity instanceof ItemEntity || entity instanceof ExperienceOrb || entity instanceof Projectile || entity == player) continue;
                    player.attack(entity);
                    attackedAny = true;
                }
                if (!attackedAny) player.resetAttackStrengthTicker();
                ((PlayerAttackProperties) player).setComboCount(-1);
            });
        });
    }

    public static void handleBlockHit(Packets.C2S_BlockHit packet, MinecraftServer server, ServerPlayer player) {
        var world = (ServerLevel) player.level();
        var block = world.getBlockState(packet.pos());
        if (block.isAir()) return;
        var soundGroup = block.getSoundType();
        if (soundGroup != null) {
            world.playSound(null, packet.pos().getX(), packet.pos().getY(), packet.pos().getZ(),
                    soundGroup.getHitSound(), player.getSoundSource(), 1.0F, 1.0F);
        }
    }
}
