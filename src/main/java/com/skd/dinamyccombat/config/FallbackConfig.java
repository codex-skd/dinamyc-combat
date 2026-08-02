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
        specifiers.add(new CompatibilitySpecifier("bettercombat:.*", "dinamyc_combat:sword"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_sword", "dinamyc_combat:sword"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_axe", "dinamyc_combat:axe"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_spear", "dinamyc_combat:spear"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_dagger", "dinamyc_combat:dagger"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_mace", "dinamyc_combat:mace"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_hammer", "dinamyc_combat:hammer"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_halberd", "dinamyc_combat:halberd"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_glaive", "dinamyc_combat:glaive"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_scythe", "dinamyc_combat:scythe"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_katana", "dinamyc_combat:katana"));
        specifiers.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_claymore", "dinamyc_combat:claymore"));
        config.fallback_compatibility = specifiers.toArray(new CompatibilitySpecifier[0]);

        List<CompatibilitySpecifier> ranged = new ArrayList<>();
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_gun", "dinamyc_combat:bow_two_handed_light"));
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_staff", "dinamyc_combat:staff"));
        ranged.add(new CompatibilitySpecifier("(?!minecraft:)(.*)_wand", "dinamyc_combat:staff"));
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
