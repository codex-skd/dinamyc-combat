package com.skd.dinamyccombat.mixin.client;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackTooltipMixin {

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void dinamyc_combat$addAttackRangeTooltip(Item.TooltipContext context, Player player,
            TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        if (player != null) {
            List<Component> tooltip = cir.getReturnValue();
            ItemStack stack = (ItemStack) (Object) this;
            double attackRange = getWeaponRange(stack);
            if (attackRange > 0) {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("dinamyc_combat.tooltip.attack_range",
                        String.format("%.1f", attackRange)));
            }
        }
    }

    private static double getWeaponRange(ItemStack stack) {
        return 0.0;
    }
}
