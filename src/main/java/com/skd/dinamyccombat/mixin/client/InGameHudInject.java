package com.skd.dinamyccombat.mixin.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudInject {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private int screenWidth;

    @Shadow
    private int screenHeight;

    @Inject(require = 0, method = "render", at = @At("HEAD"))
    private void dinamyc_combat$onRender(Object graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
    }

    @Unique
    private int getComboCount() {
        return 0;
    }
}
