package com.skd.dinamyccombat.client;

import com.skd.dinamyccombat.logic.ClientPlayerAttackProperties;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ComboHudOverlay {
    public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) return;
        if (!(client.player instanceof ClientPlayerAttackProperties props)) return;

        int combo = props.getClientComboCount();
        if (combo <= 0) return;

        Font font = client.font;
        String text = "x" + combo;
        int textWidth = font.width(text);
        int x = (guiGraphics.guiWidth() - textWidth) / 2;
        int y = guiGraphics.guiHeight() - 55;

        guiGraphics.text(font, Component.literal(text), x, y, 0xFFFFAA00);
    }
}
