package com.skd.dinamyccombat.mixin.client;

import com.zigythebird.playeranim.accessors.IAvatarAnimationState;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorBodyFixMixin {

    @Inject(method = "submit", at = @At("TAIL"))
    private void dinamyc_combat$restoreBodyArmor(CallbackInfo ci,
                                                  HumanoidRenderState renderState) {
        if (!(renderState instanceof IAvatarAnimationState state)) return;
        if (!state.playerAnimLib$isFirstPersonPass()) return;

        var manager = state.playerAnimLib$getAnimManager();
        if (manager == null) return;

        FirstPersonConfiguration config = manager.getFirstPersonConfiguration();
        if (config != null && config.isShowArmor()) {
            @SuppressWarnings("unchecked")
            var model = (HumanoidModel<HumanoidRenderState>) ((HumanoidArmorLayer<?, ?, ?>) (Object) this).getParentModel();
            model.body.visible = true;
            model.leftLeg.visible = true;
            model.rightLeg.visible = true;
            model.head.visible = true;
            model.hat.visible = true;
        }
    }
}
