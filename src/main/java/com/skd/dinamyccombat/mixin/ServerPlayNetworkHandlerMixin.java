package com.skd.dinamyccombat.mixin;

import com.skd.dinamyccombat.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayer player;

    @ModifyVariable(method = "handleInteract", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private InteractionHand dinamyc_combat$modifyInteractHand(InteractionHand hand) {
        return hand;
    }

    @ModifyVariable(method = "handleUseItemOn", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private InteractionHand dinamyc_combat$modifyUseItemHand(InteractionHand hand) {
        return hand;
    }
}
