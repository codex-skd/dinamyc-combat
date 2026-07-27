package com.skd.dinamyccombat.neoforge;

import com.skd.dinamyccombat.DinamycCombat;
import com.skd.dinamyccombat.api.component.BetterCombatDataComponents;
import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.neoforge.attachment.DinamycCombatPlayerAttachments;
import com.skd.dinamyccombat.client.ClientNetwork;
import com.skd.dinamyccombat.network.Packets;
import com.skd.dinamyccombat.network.ServerNetwork;
import com.skd.dinamyccombat.utils.SoundHelper;
import com.skd.dinamyccombat.particle.BetterCombatParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(DinamycCombat.MODID)
public final class DinamycCombatNeoForge {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DinamycCombat.MODID);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, DinamycCombat.MODID);

    static {
        SoundHelper.soundKeys.forEach(key ->
                SOUND_EVENTS.register(key, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(DinamycCombat.MODID, key))));
    }

    public DinamycCombatNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        DinamycCombat.init();
        BetterCombatDataComponents.init(modEventBus);
        DinamycCombatPlayerAttachments.init(ATTACHMENT_TYPES);
        modEventBus.addListener(RegisterEvent.class, event -> {
            if (event.getRegistryKey() == Registries.PARTICLE_TYPE) {
                BetterCombatParticles.register();
            }
        });
        SOUND_EVENTS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            var registrar = event.registrar(DinamycCombat.MODID).versioned("1");
            registrar.playToServer(Packets.C2S_AttackRequest.PACKET_ID, Packets.C2S_AttackRequest.CODEC,
                    (packet, context) -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        ServerNetwork.handleAttackRequest(packet, null, player, player.connection);
                    });
            registrar.playToServer(Packets.C2S_BlockHit.PACKET_ID, Packets.C2S_BlockHit.CODEC,
                    (packet, context) -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        ServerNetwork.handleBlockHit(packet, null, player);
                    });
            registrar.playToClient(Packets.AttackAnimation.PACKET_ID, Packets.AttackAnimation.CODEC,
                    (packet, context) -> ClientNetwork.handleAttackAnimation(packet));
            registrar.playToClient(Packets.AttackSound.PACKET_ID, Packets.AttackSound.CODEC,
                    (packet, context) -> ClientNetwork.handleAttackSound(packet));
            registrar.playToClient(Packets.WeaponRegistrySync.PACKET_ID, Packets.WeaponRegistrySync.CODEC,
                    (packet, context) -> ClientNetwork.handleWeaponRegistrySync(packet));
            registrar.playToClient(Packets.ConfigSync.PACKET_ID, Packets.ConfigSync.CODEC,
                    (packet, context) -> ClientNetwork.handleConfigSync(packet));
        });
    }
}
