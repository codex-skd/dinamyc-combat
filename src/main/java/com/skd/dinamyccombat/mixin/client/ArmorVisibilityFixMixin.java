package com.skd.dinamyccombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zigythebird.playeranim.accessors.IAvatarAnimationState;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.zigythebird.playeranim.mixin.firstPerson.HumanoidArmorLayerMixin")
public abstract class ArmorVisibilityFixMixin {

    @Inject(method = "modifyArmorVisibility", at = @At("TAIL"))
    private void dinamyc_combat$restoreBodyArmor(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                                  ItemStack itemStack, EquipmentSlot equipmentSlot, int i,
                                                  HumanoidRenderState humanoidRenderState, CallbackInfo ci,
                                                  HumanoidModel<?> humanoidModel) {
        if (!(humanoidRenderState instanceof IAvatarAnimationState state)) return;
        if (!state.playerAnimLib$isFirstPersonPass()) return;

        var manager = state.playerAnimLib$getAnimManager();
        if (manager == null) return;

        FirstPersonConfiguration config = manager.getFirstPersonConfiguration();
        if (config != null && config.isShowArmor()) {
            humanoidModel.body.visible = true;
            humanoidModel.leftLeg.visible = true;
            humanoidModel.rightLeg.visible = true;
            humanoidModel.head.visible = true;
            humanoidModel.hat.visible = true;
        }
    }
}
