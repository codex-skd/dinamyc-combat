package com.skd.dinamyccombat.client;

import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ComboHudOverlay {
    public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) return;
        if (!(client.player instanceof ClientPlayerAttackProperties props)) return;

        int step = props.getComboStep();
        int total = props.getComboTotal();
        if (step <= 0 || total <= 0) return;

        String text = step + "/" + total;
        int textWidth = client.font.width(text);
        int x = (guiGraphics.guiWidth() - textWidth) / 2;
        int y = guiGraphics.guiHeight() - 55;

        guiGraphics.text(client.font, Component.literal(text), x, y, 0xFFFFAA00);
    }
}
