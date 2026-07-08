package com.skd.dinamyccombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zigythebird.playeranim.accessors.IAvatarAnimationState;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorBodyFixMixin {

    @Inject(method = "submit", at = @At("TAIL"))
    private void dinamyc_combat$restoreBodyArmor(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                                  int i, HumanoidRenderState renderState, float f, float g,
                                                  CallbackInfo ci) {
        if (!(renderState instanceof IAvatarAnimationState state)) return;
        if (!state.playerAnimLib$isFirstPersonPass()) return;

        var manager = state.playerAnimLib$getAnimManager();
        if (manager == null) return;

        FirstPersonConfiguration config = manager.getFirstPersonConfiguration();
        if (config != null && config.isShowArmor()) {
            @SuppressWarnings("unchecked")
            var self = (HumanoidArmorLayer<?, ?, ?>) (Object) this;
            var model = (HumanoidModel<?>) self.getParentModel();
            model.body.visible = true;
            model.leftLeg.visible = true;
            model.rightLeg.visible = true;
            model.head.visible = true;
            model.hat.visible = true;
        }
    }
}
