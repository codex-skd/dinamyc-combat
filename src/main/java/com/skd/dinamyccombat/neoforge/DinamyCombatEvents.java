package com.skd.dinamyccombat.neoforge;

import com.skd.dinamyccombat.DinamyCombat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@EventBusSubscriber(modid = DinamyCombat.MODID)
public class DinamyCombatEvents {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        DinamyCombat.loadWeaponAttributes(event.getServer());
    }
}
