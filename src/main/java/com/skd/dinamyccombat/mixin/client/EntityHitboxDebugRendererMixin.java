package com.skd.dinamyccombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class EntityHitboxDebugRendererMixin {

    @Inject(method = "renderHitbox", at = @At("TAIL"))
    private static void dinamyc_combat$renderWeaponOBB(PoseStack poseStack, MultiBufferSource buffers,
            Entity entity, float partialTicks, int color, CallbackInfo ci) {
    }
}
