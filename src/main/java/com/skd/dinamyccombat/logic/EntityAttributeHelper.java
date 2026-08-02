package com.skd.dinamyccombat.logic;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class EntityAttributeHelper {
    public static boolean itemHasRangeAttribute(ItemStack stack) {
        var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (var entry : modifiers.modifiers()) {
                if (entry.attribute().is(Attributes.ENTITY_INTERACTION_RANGE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int rangeModifierCount(ItemStack stack) {
        var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            int count = 0;
            for (var entry : modifiers.modifiers()) {
                if (entry.attribute().is(Attributes.ENTITY_INTERACTION_RANGE)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }
}
