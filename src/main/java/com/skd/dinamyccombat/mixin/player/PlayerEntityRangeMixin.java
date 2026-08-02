package com.skd.dinamyccombat.mixin.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.skd.dinamyccombat.logic.PlayerAttackHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerEntityRangeMixin {

    @WrapOperation(
            method = "entityInteractionRange",
            require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttributeValue(Lnet/minecraft/core/Holder;)D")
    )
    private double dinamyc_combat$getEntityInteractionRange(Player instance, Holder<Attribute> holder, Operation<Double> original) {
        var originalResult = original.call(instance, holder);
        return PlayerAttackHelper.getRangeWithWeapon(instance, originalResult);
    }
}
