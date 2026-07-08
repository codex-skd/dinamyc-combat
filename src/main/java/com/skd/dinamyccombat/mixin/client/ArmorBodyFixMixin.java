package com.skd.dinamyccombat.mixin.client;

import com.zigythebird.playeranim.accessors.IAvatarAnimationState;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorBodyFixMixin {

    @Inject(method = "submit", at = @At("RETURN"))
    private void dinamyc_combat$restoreBodyArmor(CallbackInfo ci) {
        @SuppressWarnings("unchecked")
        var self = (HumanoidArmorLayer<?, ?, ?>) (Object) this;
        var model = (HumanoidModel<?>) self.getParentModel();

        if (model.hat != null) model.hat.visible = true;
        if (model.head != null) model.head.visible = true;
        if (model.body != null) model.body.visible = true;
        if (model.leftLeg != null) model.leftLeg.visible = true;
        if (model.rightLeg != null) model.rightLeg.visible = true;
    }
}
