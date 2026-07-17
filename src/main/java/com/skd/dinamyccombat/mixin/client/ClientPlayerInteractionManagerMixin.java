package com.skd.dinamyccombat.mixin.client;

import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        // Check main hand
        if (isWeapon(minecraft.player.getMainHandItem())) return true;
        // Check off-hand
        if (isWeapon(minecraft.player.getOffhandItem())) return true;
        return false;
    }

    private boolean isWeapon(ItemStack stack) {
        var attributes = WeaponRegistry.getAttributes(stack);
        if (attributes == null) return false;
        if (!ClientConfig.IS_AXE_CONSIDERED_WEAPON.get()
                && attributes.category() != null
                && attributes.category().equals("axe")) {
            return false;
        }
        return true;
    }
}
