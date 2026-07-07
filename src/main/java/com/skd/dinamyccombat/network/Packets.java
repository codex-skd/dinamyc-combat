package com.skd.dinamyccombat.network;

import com.google.gson.Gson;
import com.skd.dinamyccombat.api.fx.ParticlePlacement;
import com.skd.dinamyccombat.api.fx.TrailAppearance;
import com.skd.dinamyccombat.logic.AnimatedHand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Packets {

    public static final String StopSymbol = "stop";

    public record C2S_AttackRequest(int comboCount, boolean isSneaking, int selectedSlot,
                                    int cursorTarget, int[] entityIds) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "c2s_attack_request");
        public static final CustomPacketPayload.Type<C2S_AttackRequest> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2S_AttackRequest> CODEC =
                StreamCodec.of((RegistryFriendlyByteBuf buf, C2S_AttackRequest msg) -> msg.write(buf), C2S_AttackRequest::read);
        public static boolean UseVanillaPacket = false;

        public C2S_AttackRequest(int comboCount, boolean isSneaking, int selectedSlot,
                                 Entity cursorTarget, List<Entity> entities) {
            this(comboCount, isSneaking, selectedSlot,
                    convertEntity(cursorTarget), convertEntityList(entities));
        }

        public C2S_AttackRequest(int comboCount, boolean isSneaking, int selectedSlot,
                                 int cursorTarget, int[] entityIds) {
            this.comboCount = comboCount;
            this.isSneaking = isSneaking;
            this.selectedSlot = selectedSlot;
            this.cursorTarget = cursorTarget;
            this.entityIds = entityIds;
        }

        private static int[] convertEntityList(List<Entity> entities) {
            if (entities == null || entities.isEmpty()) {
                return new int[0];
            }
            int[] ids = new int[entities.size()];
            for (int i = 0; i < entities.size(); i++) {
                ids[i] = convertEntity(entities.get(i));
            }
            return ids;
        }

        private static int convertEntity(Entity entity) {
            return entity != null ? entity.getId() : -1;
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(comboCount);
            buf.writeBoolean(isSneaking);
            buf.writeVarInt(selectedSlot);
            buf.writeVarInt(cursorTarget);
            buf.writeVarIntArray(entityIds);
        }

        public static C2S_AttackRequest read(FriendlyByteBuf buf) {
            int comboCount = buf.readVarInt();
            boolean isSneaking = buf.readBoolean();
            int selectedSlot = buf.readVarInt();
            int cursorTarget = buf.readVarInt();
            int[] entityIds = buf.readVarIntArray();
            return new C2S_AttackRequest(comboCount, isSneaking, selectedSlot, cursorTarget, entityIds);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record SwingParticles(List<ParticlePlacement> particles, TrailAppearance appearance) {
        public static final SwingParticles EMPTY = new SwingParticles(List.of(), TrailAppearance.EMPTY);
    }

    public record AttackAnimation(int playerId, AnimatedHand animatedHand, String animationName,
                                  float length, float upswing, float weaponRange, int upswingTicks,
                                  SwingParticles particles) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "attack_animation");
        public static final CustomPacketPayload.Type<AttackAnimation> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<RegistryFriendlyByteBuf, AttackAnimation> CODEC =
                StreamCodec.of((RegistryFriendlyByteBuf buf, AttackAnimation msg) -> msg.write(buf), AttackAnimation::read);
        private static final Gson gson = new Gson();

        public static AttackAnimation stop(int playerId, int upswingTicks) {
            return new AttackAnimation(playerId, AnimatedHand.MAIN_HAND, StopSymbol,
                    0f, 0f, 0f, upswingTicks, SwingParticles.EMPTY);
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(playerId);
            buf.writeEnum(animatedHand);
            buf.writeUtf(animationName);
            buf.writeFloat(length);
            buf.writeFloat(upswing);
            buf.writeFloat(weaponRange);
            buf.writeVarInt(upswingTicks);
            String particlesJson = gson.toJson(particles);
            buf.writeUtf(particlesJson);
        }

        public static AttackAnimation read(FriendlyByteBuf buf) {
            int playerId = buf.readVarInt();
            AnimatedHand animatedHand = buf.readEnum(AnimatedHand.class);
            String animationName = buf.readUtf();
            float length = buf.readFloat();
            float upswing = buf.readFloat();
            float weaponRange = buf.readFloat();
            int upswingTicks = buf.readVarInt();
            String particlesJson = buf.readUtf();
            SwingParticles particles;
            try {
                particles = gson.fromJson(particlesJson, SwingParticles.class);
            } catch (Exception e) {
                particles = SwingParticles.EMPTY;
            }
            return new AttackAnimation(playerId, animatedHand, animationName,
                    length, upswing, weaponRange, upswingTicks, particles);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record AttackSound(double x, double y, double z, String soundId,
                              float volume, float pitch, long seed) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "attack_sound");
        public static final CustomPacketPayload.Type<AttackSound> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<RegistryFriendlyByteBuf, AttackSound> CODEC =
                StreamCodec.of((RegistryFriendlyByteBuf buf, AttackSound msg) -> msg.write(buf), AttackSound::read);

        public void write(FriendlyByteBuf buf) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeUtf(soundId);
            buf.writeFloat(volume);
            buf.writeFloat(pitch);
            buf.writeLong(seed);
        }

        public static AttackSound read(FriendlyByteBuf buf) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            String soundId = buf.readUtf();
            float volume = buf.readFloat();
            float pitch = buf.readFloat();
            long seed = buf.readLong();
            return new AttackSound(x, y, z, soundId, volume, pitch, seed);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record WeaponRegistrySync(boolean compressed,
                                      List<String> chunks) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "weapon_registry");
        public static final CustomPacketPayload.Type<WeaponRegistrySync> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, WeaponRegistrySync> CODEC =
                StreamCodec.of((FriendlyByteBuf buf, WeaponRegistrySync msg) -> msg.write(buf), WeaponRegistrySync::read);

        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(compressed);
            buf.writeVarInt(chunks.size());
            for (String chunk : chunks) {
                buf.writeUtf(chunk);
            }
        }

        public static WeaponRegistrySync read(FriendlyByteBuf buf) {
            boolean compressed = buf.readBoolean();
            int count = buf.readVarInt();
            List<String> chunks = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                chunks.add(buf.readUtf());
            }
            return new WeaponRegistrySync(compressed, chunks);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record C2S_BlockHit(BlockPos pos) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "c2s_block_hit");
        public static final CustomPacketPayload.Type<C2S_BlockHit> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, C2S_BlockHit> CODEC =
                StreamCodec.of((FriendlyByteBuf buf, C2S_BlockHit msg) -> msg.write(buf), C2S_BlockHit::read);

        public void write(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
        }

        public static C2S_BlockHit read(FriendlyByteBuf buf) {
            return new C2S_BlockHit(buf.readBlockPos());
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record ConfigSync(String json) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "config_sync");
        public static final CustomPacketPayload.Type<ConfigSync> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, ConfigSync> CODEC =
                StreamCodec.of((FriendlyByteBuf buf, ConfigSync msg) -> msg.write(buf), ConfigSync::read);
        private static final Gson gson = new Gson();

        public void write(FriendlyByteBuf buf) {
            buf.writeUtf(json);
        }

        public static ConfigSync read(FriendlyByteBuf buf) {
            return new ConfigSync(buf.readUtf());
        }

        public String json() {
            return json;
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }

    public record Ack(String code) implements CustomPacketPayload {
        private static final Identifier ID = Identifier.fromNamespaceAndPath("dinamyc_combat", "ack");
        public static final CustomPacketPayload.Type<Ack> PACKET_ID = new CustomPacketPayload.Type<>(ID);
        public static final StreamCodec<FriendlyByteBuf, Ack> CODEC =
                StreamCodec.of((FriendlyByteBuf buf, Ack msg) -> msg.write(buf), Ack::read);

        public void write(FriendlyByteBuf buf) {
            buf.writeUtf(code);
        }

        public static Ack read(FriendlyByteBuf buf) {
            return new Ack(buf.readUtf());
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PACKET_ID;
        }
    }
}
