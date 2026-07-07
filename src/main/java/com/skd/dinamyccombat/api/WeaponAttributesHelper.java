package com.skd.dinamyccombat.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.skd.dinamyccombat.DinamyCombat;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;

public class WeaponAttributesHelper {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Identifier.class, (JsonSerializer<Identifier>) (src, type, context) ->
                    new JsonPrimitive(src.toString()))
            .registerTypeAdapter(Identifier.class, (JsonDeserializer<Identifier>) (json, type, context) ->
                    Identifier.parse(json.getAsString()))
            .setPrettyPrinting()
            .create();

    public static ItemStack override(ItemStack itemStack, WeaponAttributes attributes) {
        if (attributes == null) {
            return itemStack;
        }
        var modifiers = new ArrayList<>(itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY).modifiers());
        modifiers.removeIf(entry -> entry.attribute().is(Attributes.ATTACK_DAMAGE)
                || entry.attribute().is(Attributes.ATTACK_SPEED)
                || entry.attribute().is(Attributes.BLOCK_INTERACTION_RANGE));

        if (attributes.damage() != 0) {
            modifiers.add(new ItemAttributeModifiers.Entry(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            Identifier.fromNamespaceAndPath(DinamyCombat.MODID, "weapon_damage_override"),
                            attributes.damage(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
            ));
        }
        if (attributes.speed() != 0) {
            modifiers.add(new ItemAttributeModifiers.Entry(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            Identifier.fromNamespaceAndPath(DinamyCombat.MODID, "weapon_speed_override"),
                            attributes.speed(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
            ));
        }
        if (attributes.attackRange() != 0) {
            modifiers.add(new ItemAttributeModifiers.Entry(
                    Attributes.BLOCK_INTERACTION_RANGE,
                    new AttributeModifier(
                            Identifier.fromNamespaceAndPath(DinamyCombat.MODID, "weapon_range_override"),
                            attributes.attackRange(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.MAINHAND
            ));
        }

        itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers));
        return itemStack;
    }

    public static boolean validate(WeaponAttributes attributes) {
        if (attributes == null) {
            return false;
        }
        return true;
    }

    public static WeaponAttributes decode(String json) {
        try {
            return GSON.fromJson(json, WeaponAttributes.class);
        } catch (JsonSyntaxException e) {
            DinamyCombat.LOGGER.error("Failed to decode WeaponAttributes from JSON: {}", json, e);
            return null;
        }
    }

    public static String encode(WeaponAttributes attributes) {
        return GSON.toJson(attributes);
    }

    public static Gson gson() {
        return GSON;
    }
}
