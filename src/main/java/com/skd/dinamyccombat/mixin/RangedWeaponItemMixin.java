package com.skd.dinamyccombat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.skd.dinamyccombat.logic.InventoryUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class RangedWeaponItemMixin {

    @WrapOperation(
            method = "getProjectile",
            require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;")
    )
    private static ItemStack dinamyc_combat$getHeldProjectile(LivingEntity entity, InteractionHand hand, Operation<ItemStack> original) {
        if (entity instanceof Player player) {
            return InventoryUtil.getOffHandSlotStack(player);
        }
        return original.call(entity, hand);
    }
}
