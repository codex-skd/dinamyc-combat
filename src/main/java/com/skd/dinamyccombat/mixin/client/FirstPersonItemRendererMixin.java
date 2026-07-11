package com.skd.dinamyccombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
public class FirstPersonItemRendererMixin {

    @Unique
    private static final float SWING_SCALE = 1.0f;

    @Unique
    private final PlayerAnimBone dinamyc_combat$rightArm = new PlayerAnimBone("right_arm");
    @Unique
    private final PlayerAnimBone dinamyc_combat$leftArm = new PlayerAnimBone("left_arm");

    @Inject(method = "renderItem", at = @At("HEAD"))
    private void dinamyc_combat$applyArmRotation(
            LivingEntity entity, ItemStack itemStack, ItemDisplayContext transformType,
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, CallbackInfo ci) {
        if (entity != Minecraft.getInstance().getCameraEntity()) return;
        if (!(entity instanceof IAnimatedAvatar animated)) return;
        if (Minecraft.getInstance().gameRenderer.getMainCamera().isDetached()) return;
        var animManager = animated.playerAnimLib$getAnimManager();
        if (animManager == null || !animManager.isActive()) return;

        PlayerAnimBone bone = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                ? this.dinamyc_combat$leftArm : this.dinamyc_combat$rightArm;
        bone.setToInitialPose();
        animManager.get3DTransform(bone);

        if (bone.rotation.z != 0.0F) {
            poseStack.mulPose(Axis.ZP.rotation(-bone.rotation.y * SWING_SCALE));
        }
        if (bone.rotation.y != 0.0F) {
            poseStack.mulPose(Axis.YP.rotation(-bone.rotation.z * SWING_SCALE));
        }
        if (bone.rotation.x != 0.0F) {
            poseStack.mulPose(Axis.XP.rotation(-bone.rotation.x * SWING_SCALE));
        }
    }
}
