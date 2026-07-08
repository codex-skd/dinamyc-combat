package com.skd.dinamyccombat.mixin.client;

import com.zigythebird.playeranim.accessors.IAvatarAnimationState;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer")
public abstract class ArmorBodyFixMixin {

    @Inject(method = "renderArmorPiece", at = @At("TAIL"))
    private void dinamyc_combat$restoreBodyArmor(CallbackInfo ci,
                                                  HumanoidRenderState renderState,
                                                  HumanoidModel<?> model,
                                                  EquipmentSlot slot,
                                                  ItemStack itemStack) {
        if (!(renderState instanceof IAvatarAnimationState state)) return;
        if (!state.playerAnimLib$isFirstPersonPass()) return;

        var manager = state.playerAnimLib$getAnimManager();
        if (manager == null) return;

        FirstPersonConfiguration config = manager.getFirstPersonConfiguration();
        if (config != null && config.isShowArmor()) {
            model.body.visible = true;
            model.leftLeg.visible = true;
            model.rightLeg.visible = true;
            model.head.visible = true;
            model.hat.visible = true;
        }
    }
}
