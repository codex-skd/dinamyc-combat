package com.skd.dinamyccombat.mixin;

import com.skd.dinamyccombat.logic.InventoryUtil;
import com.skd.dinamyccombat.mixin.player.PlayerEntityAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayer player;

    @Redirect(method = "handlePlayerAction",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"),
            require = 0)
    public ItemStack dinamyc_combat$getStackInHand(ServerPlayer instance, InteractionHand hand) {
        return switch (hand) {
            case MAIN_HAND -> instance.getMainHandItem();
            case OFF_HAND -> InventoryUtil.getOffHandSlotStack(instance);
        };
    }
}
