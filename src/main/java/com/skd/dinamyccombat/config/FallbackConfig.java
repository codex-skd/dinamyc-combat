package com.skd.dinamyccombat.config;

import java.util.ArrayList;
import java.util.List;

public class FallbackConfig {
    public int schema_version;
    public String blacklist_item_id_regex;
    public CompatibilitySpecifier[] fallback_compatibility;
    public CompatibilitySpecifier[] ranged_weapons;

    public FallbackConfig() {
        this.schema_version = 1;
        this.blacklist_item_id_regex = "";
        this.fallback_compatibility = new CompatibilitySpecifier[0];
        this.ranged_weapons = new CompatibilitySpecifier[0];
    }

    public FallbackConfig(int schema_version, String blacklist_item_id_regex,
                          CompatibilitySpecifier[] fallback_compatibility,
                          CompatibilitySpecifier[] ranged_weapons) {
        this.schema_version = schema_version;
        this.blacklist_item_id_regex = blacklist_item_id_regex;
        this.fallback_compatibility = fallback_compatibility;
        this.ranged_weapons = ranged_weapons;
    }

    public static FallbackConfig createDefault() {
        FallbackConfig config = new FallbackConfig();
        config.schema_version = 1;

        List<CompatibilitySpecifier> specifiers = new ArrayList<>();
        specifiers.add(new CompatibilitySpecifier("bettercombat:.*", "{\"attributes\":{\"attack_range\":2.5,\"category\":\"axe\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_sword", "{\"attributes\":{\"attack_range\":2.5,\"category\":\"sword\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_axe", "{\"attributes\":{\"attack_range\":2.5,\"category\":\"axe\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_spear", "{\"attributes\":{\"attack_range\":3.0,\"pose\":\"two_handed\",\"category\":\"spear\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_dagger", "{\"attributes\":{\"attack_range\":1.8,\"category\":\"dagger\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_mace", "{\"attributes\":{\"attack_range\":2.5,\"category\":\"mace\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_hammer", "{\"attributes\":{\"attack_range\":2.5,\"category\":\"mace\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_halberd", "{\"attributes\":{\"attack_range\":3.5,\"category\":\"halberd\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_glaive", "{\"attributes\":{\"attack_range\":3.5,\"category\":\"glaive\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_scythe", "{\"attributes\":{\"attack_range\":3.0,\"category\":\"scythe\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_katana", "{\"attributes\":{\"attack_range\":2.8,\"category\":\"katana\"}}"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_claymore", "{\"attributes\":{\"attack_range\":3.2,\"category\":\"claymore\"}}"));
        config.fallback_compatibility = specifiers.toArray(new CompatibilitySpecifier[0]);

        List<CompatibilitySpecifier> ranged = new ArrayList<>();
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_gun", "{\"attributes\":{\"attack_range\":-1}}"));
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_staff", "{\"attributes\":{\"attack_range\":-1}}"));
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_wand", "{\"attributes\":{\"attack_range\":-1}}"));
        config.ranged_weapons = ranged.toArray(new CompatibilitySpecifier[0]);

        return config;
    }

    public static FallbackConfig migrate(FallbackConfig current, FallbackConfig freshDefaults) {
        if (current == null) {
            return freshDefaults;
        }
        if (current.schema_version < freshDefaults.schema_version) {
            FallbackConfig merged = new FallbackConfig();
            merged.schema_version = freshDefaults.schema_version;
            merged.blacklist_item_id_regex = freshDefaults.blacklist_item_id_regex;

            CompatibilitySpecifier[] currentCompat = current.fallback_compatibility;
            CompatibilitySpecifier[] freshCompat = freshDefaults.fallback_compatibility;
            CompatibilitySpecifier[] mergedCompat = freshCompat;
            if (currentCompat != null && currentCompat.length > 0) {
                mergedCompat = new CompatibilitySpecifier[currentCompat.length + freshCompat.length];
                System.arraycopy(currentCompat, 0, mergedCompat, 0, currentCompat.length);
                System.arraycopy(freshCompat, 0, mergedCompat, currentCompat.length, freshCompat.length);
            }
            merged.fallback_compatibility = mergedCompat;
            merged.ranged_weapons = freshDefaults.ranged_weapons;
            return merged;
        }
        return current;
    }

    public static class CompatibilitySpecifier {
        public String item_id_regex;
        public String weapon_attributes;

        public CompatibilitySpecifier() {
            this.item_id_regex = "";
            this.weapon_attributes = "";
        }

        public CompatibilitySpecifier(String item_id_regex, String weapon_attributes) {
            this.item_id_regex = item_id_regex;
            this.weapon_attributes = weapon_attributes;
        }
    }
}
