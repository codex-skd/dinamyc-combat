package com.skd.dinamyccombat;

import com.skd.dinamyccombat.client.ClientNetwork;
import com.skd.dinamyccombat.network.Packets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(value = DinamyCombat.MODID, dist = Dist.CLIENT)
public class DinamyCombatClient {
    public DinamyCombatClient(ModContainer container, IEventBus modEventBus) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            var registrar = event.registrar(DinamyCombat.MODID).versioned("1");
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
