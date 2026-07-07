package com.skd.dinamyccombat;

import com.skd.dinamyccombat.client.ComboHudOverlay;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = DinamyCombat.MODID, dist = Dist.CLIENT)
public class DinamyCombatClient {
    private static final Identifier COMBO_HUD = Identifier.fromNamespaceAndPath(DinamyCombat.MODID, "combo_hud");

    public DinamyCombatClient(ModContainer container, IEventBus modEventBus) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(RegisterGuiLayersEvent.class, event -> {
            event.registerAboveAll(COMBO_HUD, ComboHudOverlay::render);
        });
    }
}
