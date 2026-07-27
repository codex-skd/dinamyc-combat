package com.skd.dinamyccombat;

import com.skd.dinamyccombat.client.ClientNetwork;
import com.skd.dinamyccombat.client.effect.WeaponEffectManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = DinamycCombat.MODID, dist = Dist.CLIENT)
public class DinamycCombatClient {
    public DinamycCombatClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        ClientNetwork.init();
        NeoForge.EVENT_BUS.addListener(DinamycCombatClient::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        WeaponEffectManager.clientTick();
    }
}
