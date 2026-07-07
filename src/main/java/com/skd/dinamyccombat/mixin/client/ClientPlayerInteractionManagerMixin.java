package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private boolean isDestroying;

    @Inject(method = "attack", at = @At("HEAD"))
    private void dinamyc_combat$onAttackEntity(Player player, Entity target, CallbackInfo ci) {
    }

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$onStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (shouldPreventMining()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void dinamyc_combat$onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (shouldPreventMining()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "stopDestroyBlock", at = @At("HEAD"))
    private void dinamyc_combat$onStopDestroyBlock(CallbackInfo ci) {
    }

    private boolean shouldPreventMining() {
        if (ClientConfig.IS_MINING_WITH_WEAPONS_ENABLED.get()) {
            return false;
        }
        if (minecraft.player == null) return false;
        var attributes = WeaponRegistry.getAttributes(minecraft.player.getMainHandItem());
        if (attributes == null) {
            attributes = WeaponRegistry.getAttributes(minecraft.player.getOffhandItem());
        }
        return attributes != null;
    }
}
