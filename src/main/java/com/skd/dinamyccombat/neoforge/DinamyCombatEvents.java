package com.skd.dinamyccombat.neoforge;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.network.Packets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = DinamyCombat.MODID)
public class DinamyCombatEvents {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        DinamyCombat.loadWeaponAttributes(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            var encoded = WeaponRegistry.getEncodedRegistry();
            PacketDistributor.sendToPlayer(player, new Packets.WeaponRegistrySync(encoded.compressed(), encoded.chunks()));
        }
    }
}
