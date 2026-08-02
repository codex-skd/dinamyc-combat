package com.skd.dinamyccombat.mixin;

import com.skd.dinamyccombat.logic.InventoryUtil;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileWeaponItem.class)
public class RangedWeaponItemMixin {

    @Inject(
            method = "getHeldProjectile",
            require = 0,
            at = @At("HEAD"),
            cancellable = true
    )
    private static void dinamyc_combat$getHeldProjectile(LivingEntity entity, Predicate<ItemStack> valid, CallbackInfoReturnable<ItemStack> cir) {
        if (entity instanceof Player player) {
            cir.setReturnValue(InventoryUtil.getOffHandSlotStack(player));
        }
    }
}
