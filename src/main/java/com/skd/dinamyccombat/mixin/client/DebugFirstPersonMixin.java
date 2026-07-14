package com.skd.dinamyccombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.dinamyccombat.DinamyCombat;
import com.skd.playeranimationcore.accessors.IAnimatedAvatar;
import com.skd.playeranimationcore.bones.PlayerAnimBone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class DebugFirstPersonMixin {

    @Unique
    private final PlayerAnimBone dinamyc_debug$rightArm = new PlayerAnimBone("right_arm");
    @Unique
    private final PlayerAnimBone dinamyc_debug$leftArm = new PlayerAnimBone("left_arm");
    @Unique
    private int dinamyc_debug$frameCount = 0;

    @Inject(method = "renderItem", at = @At("HEAD"))
    private void dinamyc_debug$logBoneValues(
            LivingEntity entity, ItemStack itemStack, ItemDisplayContext transformType,
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, CallbackInfo ci) {
        if (entity != Minecraft.getInstance().getCameraEntity()) return;
        if (!(entity instanceof IAnimatedAvatar animated)) return;
        var animManager = animated.playerAnimLib$getAnimManager();
        if (animManager == null || !animManager.isActive()) {
            if (dinamyc_debug$frameCount > 0) {
                DinamyCombat.LOGGER.info("[FP-DEBUG] Animation ended");
                dinamyc_debug$frameCount = 0;
            }
            return;
        }

        PlayerAnimBone bone = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                ? this.dinamyc_debug$leftArm : this.dinamyc_debug$rightArm;
        bone.setToInitialPose();
        animManager.get3DTransform(bone);

        float handX = (float)(Math.sin(bone.rotation.y) * 8.0f) / 16.0f;
        float handY = (float)(Math.sin(bone.rotation.x) * 8.0f * 0.6f) / 16.0f;

        DinamyCombat.LOGGER.info("[FP-DEBUG] Frame {} | mode={} | isActive=true | "
            + "rot(yaw={}, roll={}, pitch={}) | "
            + "pos(model x={}, y={}) | "
            + "handScreen(x={}, y={})",
            dinamyc_debug$frameCount++,
            animManager.getFirstPersonMode(),
            String.format("%.3f", bone.rotation.y),
            String.format("%.3f", bone.rotation.z),
            String.format("%.3f", bone.rotation.x),
            String.format("%.3f", bone.position.x),
            String.format("%.3f", bone.position.y),
            String.format("%.3f", handX),
            String.format("%.3f", handY));
    }
}
