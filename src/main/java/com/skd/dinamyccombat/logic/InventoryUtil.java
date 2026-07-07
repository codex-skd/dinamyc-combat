package com.skd.dinamyccombat.logic;

import com.skd.dinamyccombat.mixin.player.PlayerInventoryAccessor;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    public static ItemStack getOffHandSlotStack(Player player) {
        NonNullList<ItemStack> items = ((PlayerInventoryAccessor) player.getInventory()).getItems();
        if (items.size() <= Inventory.SLOT_OFFHAND) {
            return ItemStack.EMPTY;
        }
        return items.get(Inventory.SLOT_OFFHAND);
    }

    public static void setOffHandSlotStack(Player player, ItemStack stack) {
        NonNullList<ItemStack> items = ((PlayerInventoryAccessor) player.getInventory()).getItems();
        if (items.size() <= Inventory.SLOT_OFFHAND) {
            return;
        }
        items.set(Inventory.SLOT_OFFHAND, stack);
    }
}
