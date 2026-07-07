package com.skd.dinamyccombat.mixin.client;

import com.zigythebird.playeranimcore.animation.layered.AnimationStack;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimationStack.class)
public abstract class AvatarAnimManagerMixin {

    @Inject(method = "getFirstPersonConfiguration", at = @At("RETURN"), cancellable = true)
    private void dinamyc_combat$fixArmorVisibility(CallbackInfoReturnable<FirstPersonConfiguration> cir) {
        var current = cir.getReturnValue();
        if (current != null && !current.isShowArmor()) {
            cir.setReturnValue(new FirstPersonConfiguration(
                    current.isShowRightArm(), current.isShowLeftArm(),
                    current.isShowRightItem(), current.isShowLeftItem(),
                    true));
        }
    }
}
