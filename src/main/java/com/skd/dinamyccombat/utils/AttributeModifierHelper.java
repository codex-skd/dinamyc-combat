package com.skd.dinamyccombat.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.skd.dinamyccombat.DinamycCombat;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class AttributeModifierHelper {
    public static Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap(ItemStack itemStack) {
        var modifiers = itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS,
                net.minecraft.core.component.DataComponentPatch.EMPTY);
        Multimap<Holder<Attribute>, AttributeModifier> modifiersMap = HashMultimap.create();
        var attribs = itemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (attribs != null) {
            for (var entry : attribs.modifiers()) {
                modifiersMap.put(entry.attribute(), entry.modifier());
            }
        }
        return modifiersMap;
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> fromModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiersMap = HashMultimap.create();
        if (modifier != null) {
            modifiersMap.put(attribute, modifier);
        }
        return modifiersMap;
    }
}
