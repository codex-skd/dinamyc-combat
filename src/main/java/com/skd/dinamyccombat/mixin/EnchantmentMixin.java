package com.skd.dinamyccombat.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = "matchingSlot", at = @At("HEAD"), cancellable = true)
    private static void dinamyc_combat$modifyMatchingSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    }

    @Inject(method = "getPrimaryEquipment", at = @At("HEAD"), cancellable = true)
    private static void dinamyc_combat$getPrimaryEquipment(ItemStack stack, CallbackInfoReturnable<EquipmentSlotGroup> cir) {
    }
}
