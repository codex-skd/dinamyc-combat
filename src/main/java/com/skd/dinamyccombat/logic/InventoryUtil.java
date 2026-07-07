package com.skd.dinamyccombat.logic;

import com.skd.dinamyccombat.mixin.player.PlayerInventoryAccessor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    public static ItemStack getOffHandSlotStack(Player player) {
        return ((PlayerInventoryAccessor) player.getInventory()).getItems().get(Inventory.SLOT_OFFHAND);
    }

    public static void setOffHandSlotStack(Player player, ItemStack stack) {
        ((PlayerInventoryAccessor) player.getInventory()).getItems().set(Inventory.SLOT_OFFHAND, stack);
    }
}
