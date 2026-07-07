package com.skd.dinamyccombat.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(require = 0, method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
    private void dinamyc_combat$onSendPacket(Packet<?> packet, CallbackInfo ci) {
    }

    @Inject(require = 0, method = "handleBundleDelimiter", at = @At("HEAD"))
    private void dinamyc_combat$onBundleDelimiter(CallbackInfo ci) {
    }
}
