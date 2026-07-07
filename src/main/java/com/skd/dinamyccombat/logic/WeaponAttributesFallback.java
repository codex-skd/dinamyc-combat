package com.skd.dinamyccombat.logic;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.config.FallbackConfig;
import com.skd.dinamyccombat.utils.PatternMatching;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;

public class WeaponAttributesFallback {
    private static FallbackConfig FALLBACK_CONFIG;

    public static void initialize() {
        FALLBACK_CONFIG = FallbackConfig.createDefault();
        for (var itemId : BuiltInRegistries.ITEM.keySet()) {
            var itemRef = BuiltInRegistries.ITEM.get(itemId);
            if (itemRef.isEmpty()) continue;
            Item item = itemRef.get().value();
            if (PatternMatching.matches(itemId.toString(), FALLBACK_CONFIG.blacklist_item_id_regex)) continue;
            FallbackConfig.CompatibilitySpecifier[] specifiers = null;
            if (hasAttackDamage(item))
                specifiers = FALLBACK_CONFIG.fallback_compatibility;
            else if (item instanceof ProjectileWeaponItem)
                specifiers = FALLBACK_CONFIG.ranged_weapons;
            if (specifiers == null) continue;
            for (var option : specifiers) {
                if (WeaponRegistry.getAttributes(itemId) == null
                        && PatternMatching.matches(itemId.toString(), option.item_id_regex)) {
                    var container = WeaponRegistry.containers.get(Identifier.parse(option.weapon_attributes));
                    if (container != null) {
                        WeaponRegistry.resolveAndRegisterAttributes(itemId, container);
                        break;
                    }
                }
            }
        }
    }

    private static boolean hasAttackDamage(Item item) {
        var attributes = item.components().get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (attributes == null) return false;
        for (var entry : attributes.modifiers()) {
            if (entry.attribute().is(Attributes.ATTACK_DAMAGE)) return true;
        }
        return false;
    }
}
