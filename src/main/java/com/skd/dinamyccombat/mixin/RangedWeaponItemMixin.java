package com.skd.dinamyccombat.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public class RangedWeaponItemMixin {

    @Inject(method = "getHeldProjectile", at = @At("HEAD"), cancellable = true)
    private static void dinamyc_combat$getHeldProjectile(LivingEntity entity,
            Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack offhandStack = entity.getItemInHand(InteractionHand.OFF_HAND);
        if (predicate.test(offhandStack)) {
            ItemStack mainHandStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
            if (!isTwoHandedWeapon(mainHandStack)) {
                cir.setReturnValue(offhandStack);
            }
        }
    }

    private static boolean isTwoHandedWeapon(ItemStack stack) {
        return false;
    }
}
