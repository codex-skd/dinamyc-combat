package com.skd.dinamyccombat.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue UPSWING_MULTIPLIER = BUILDER
            .comment("Multiplier applied to the upswing duration of attack animations",
                    "Higher values mean longer upswing (slower attacks)",
                    "Range: 0.0 to 2.0")
            .defineInRange("upswing_multiplier", 0.75, 0.0, 2.0);

    public static final ModConfigSpec.BooleanValue ALLOW_FAST_ATTACKS = BUILDER
            .comment("Allow attacking as fast as the weapon permits")
            .define("allow_fast_attacks", true);

    public static final ModConfigSpec.BooleanValue ALLOW_ATTACKING_MOUNT = BUILDER
            .comment("Allow attacking the entity you are currently riding")
            .define("allow_attacking_mount", false);

    public static final ModConfigSpec.IntValue ATTACK_INTERVAL_CAP = BUILDER
            .comment("Minimum number of ticks between attacks (0 = no cap)")
            .defineInRange("attack_interval_cap", 2, 0, 20);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> HOSTILE_PLAYER_VEHICLES = BUILDER
            .comment("Entity type IDs that are considered hostile when ridden by a player",
                    "Example: minecraft:boat")
            .defineListAllowEmpty("hostile_player_vehicles", ArrayList::new, () -> "", o -> o instanceof String);

    public static final ModConfigSpec.BooleanValue ALLOW_VANILLA_SWEEPING = BUILDER
            .comment("Allow vanilla sweeping edge attacks")
            .define("allow_vanilla_sweeping", false);

    public static final ModConfigSpec.BooleanValue ALLOW_REWORKED_SWEEPING = BUILDER
            .comment("Enable the reworked sweeping attack system")
            .define("allow_reworked_sweeping", true);

    public static final ModConfigSpec.IntValue REWORKED_SWEEPING_EXTRA_TARGET_COUNT = BUILDER
            .comment("Number of extra targets that can be hit by a sweeping attack",
                    "0 = only the main target is hit")
            .defineInRange("reworked_sweeping_extra_target_count", 0, 0, 20);

    public static final ModConfigSpec.DoubleValue REWORKED_SWEEPING_MAXIMUM_DAMAGE_PENALTY = BUILDER
            .comment("Maximum damage penalty for sweeping extra targets",
                    "1.0 = full damage lost, 0.0 = no penalty",
                    "The penalty scales with distance from the main target")
            .defineInRange("reworked_sweeping_maximum_damage_penalty", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue REWORKED_SWEEPING_PLAYS_SOUND = BUILDER
            .comment("Play sweeping sound on reworked sweep attacks")
            .define("reworked_sweeping_plays_sound", true);

    public static final ModConfigSpec.BooleanValue REWORKED_SWEEPING_EMITS_PARTICLES = BUILDER
            .comment("Emit sweeping particles on reworked sweep attacks")
            .define("reworked_sweeping_emits_particles", true);

    public static final ModConfigSpec.BooleanValue REWORKED_SWEEPING_SOUND_AND_PARTICLES_ONLY_FOR_SWORDS = BUILDER
            .comment("Only play sweep sounds and particles when using a sword")
            .define("reworked_sweeping_sound_and_particles_only_for_swords", true);

    public static final ModConfigSpec.BooleanValue ALLOW_ATTACKING_THRU_WALLS = BUILDER
            .comment("Allow attacks to hit targets through walls and solid blocks")
            .define("allow_attacking_thru_walls", false);

    public static final ModConfigSpec.DoubleValue MOVEMENT_SPEED_WHILE_ATTACKING = BUILDER
            .comment("Movement speed multiplier while performing an attack",
                    "1.0 = normal speed, 0.0 = stand still, lower = slower")
            .defineInRange("movement_speed_while_attacking", 0.3, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue MOVEMENT_SPEED_APPLIED_SMOOTHLY = BUILDER
            .comment("Apply movement speed changes smoothly over time instead of instantly")
            .define("movement_speed_applied_smoothly", true);

    public static final ModConfigSpec.BooleanValue MOVEMENT_SPEED_EFFECTED_WHILE_MOUNTING = BUILDER
            .comment("Apply movement speed changes while riding a mount")
            .define("movement_speed_effected_while_mounting", true);

    public static final ModConfigSpec.BooleanValue KNOCKBACK_REDUCED_FOR_FAST_ATTACKS = BUILDER
            .comment("Reduce knockback for fast consecutive attacks")
            .define("knockback_reduced_for_fast_attacks", true);

    public static final ModConfigSpec.DoubleValue KNOCKBACK_REDUCTION_THRESHOLD = BUILDER
            .comment("Attack cooldown threshold below which knockback is reduced",
                    "1.0 = full cooldown, 0.0 = no cooldown")
            .defineInRange("knockback_reduction_threshold", 0.3, 0.0, 1.0);

    public static final ModConfigSpec.EnumValue<Curve> KNOCKBACK_REDUCTION_CURVE = BUILDER
            .comment("Curve type for knockback reduction scaling",
                    "LINEAR = knockback scales linearly with cooldown",
                    "SQUARE = knockback scales quadratically",
                    "HALF_SQUARE = knockback scales with square root")
            .defineEnum("knockback_reduction_curve", Curve.LINEAR);

    public static final ModConfigSpec.DoubleValue COMBO_RESET_RATE = BUILDER
            .comment("Rate at which the combo counter resets per tick",
                    "0.0 = never reset, higher = faster reset")
            .defineInRange("combo_reset_rate", 0.0, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue TARGET_SEARCH_RANGE_MULTIPLIER = BUILDER
            .comment("Multiplier for the range at which targets are searched",
                    "Affects how easily attacks hit targets at the edge of range")
            .defineInRange("target_search_range_multiplier", 1.0, 0.5, 2.0);

    public static final ModConfigSpec.BooleanValue SERVER_TARGET_RANGE_VALIDATION = BUILDER
            .comment("Validate attack range on the server side for security")
            .define("server_target_range_validation", true);

    public static final ModConfigSpec.DoubleValue DUAL_WIELDING_ATTACK_SPEED_MULTIPLIER = BUILDER
            .comment("Multiplier for attack speed when dual wielding")
            .defineInRange("dual_wielding_attack_speed_multiplier", 1.0, 0.1, 3.0);

    public static final ModConfigSpec.DoubleValue DUAL_WIELDING_MAIN_HAND_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for the main hand weapon when dual wielding")
            .defineInRange("dual_wielding_main_hand_damage_multiplier", 0.8, 0.1, 2.0);

    public static final ModConfigSpec.DoubleValue DUAL_WIELDING_OFF_HAND_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for the off-hand weapon when dual wielding")
            .defineInRange("dual_wielding_off_hand_damage_multiplier", 0.8, 0.1, 2.0);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> PLAYER_RELATIONS = BUILDER
            .comment("Entity type relationships for targeting",
                    "Format: \"entity_type=RELATION\" where RELATION is FRIENDLY, NEUTRAL, or HOSTILE",
                    "Example: \"minecraft:villager=FRIENDLY\"",
                    "Order matters: first matching entry wins")
            .defineListAllowEmpty("player_relations", ServerConfig::defaultPlayerRelations,
                    () -> "", o -> o instanceof String s && isValidRelationEntry(s));

    public static final ModConfigSpec.ConfigValue<List<? extends String>> PLAYER_RELATION_TAGS = BUILDER
            .comment("Entity type tag relationships for targeting",
                    "Format: \"#tag_id=RELATION\" where RELATION is FRIENDLY, NEUTRAL, or HOSTILE",
                    "Example: \"#minecraft:raiders=HOSTILE\"",
                    "Order matters: first matching entry wins")
            .defineListAllowEmpty("player_relation_tags", ArrayList::new,
                    () -> "", o -> o instanceof String s && s.startsWith("#") && isValidRelationEntry(s));

    public static final ModConfigSpec.EnumValue<Relation> PLAYER_RELATION_TO_SELF_AND_PETS = BUILDER
            .comment("Target relation to yourself and your pets")
            .defineEnum("player_relation_to_self_and_pets", Relation.FRIENDLY);

    public static final ModConfigSpec.EnumValue<Relation> PLAYER_RELATION_TO_TEAMMATES = BUILDER
            .comment("Target relation to your teammates (scoreboard team)")
            .defineEnum("player_relation_to_teammates", Relation.FRIENDLY);

    public static final ModConfigSpec.EnumValue<Relation> PLAYER_RELATION_TO_PASSIVES = BUILDER
            .comment("Target relation to passive entities (by default)")
            .defineEnum("player_relation_to_passives", Relation.NEUTRAL);

    public static final ModConfigSpec.EnumValue<Relation> PLAYER_RELATION_TO_HOSTILES = BUILDER
            .comment("Target relation to hostile entities (by default)")
            .defineEnum("player_relation_to_hostiles", Relation.HOSTILE);

    public static final ModConfigSpec.EnumValue<Relation> PLAYER_RELATION_TO_OTHER = BUILDER
            .comment("Target relation to other entities not covered by above categories")
            .defineEnum("player_relation_to_other", Relation.NEUTRAL);

    public static final ModConfigSpec.BooleanValue FALLBACK_COMPATIBILITY_ENABLED = BUILDER
            .comment("Enable fallback weapon compatibility detection",
                    "When enabled, weapons from other mods get default weapon attributes")
            .define("fallback_compatibility_enabled", true);

    public static final ModConfigSpec.BooleanValue WEAPON_REGISTRY_LOGGING = BUILDER
            .comment("Log weapon registry entries during initialization")
            .define("weapon_registry_logging", true);

    public static final ModConfigSpec.BooleanValue WEAPON_REGISTRY_COMPRESSION = BUILDER
            .comment("Compress weapon registry data when syncing to clients")
            .define("weapon_registry_compression", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private static List<String> defaultPlayerRelations() {
        List<String> defaults = new ArrayList<>();
        defaults.add("minecraft:player=NEUTRAL");
        defaults.add("minecraft:villager=FRIENDLY");
        defaults.add("minecraft:iron_golem=FRIENDLY");
        defaults.add("minecraft:snow_golem=FRIENDLY");
        defaults.add("minecraft:wolf=FRIENDLY");
        defaults.add("minecraft:cat=FRIENDLY");
        defaults.add("minecraft:horse=FRIENDLY");
        defaults.add("minecraft:donkey=FRIENDLY");
        defaults.add("minecraft:mule=FRIENDLY");
        defaults.add("minecraft:llama=FRIENDLY");
        defaults.add("minecraft:trader_llama=FRIENDLY");
        defaults.add("minecraft:parrot=FRIENDLY");
        defaults.add("minecraft:turtle=FRIENDLY");
        defaults.add("minecraft:panda=FRIENDLY");
        defaults.add("minecraft:fox=FRIENDLY");
        defaults.add("minecraft:bee=FRIENDLY");
        defaults.add("minecraft:axolotl=FRIENDLY");
        defaults.add("minecraft:allay=FRIENDLY");
        defaults.add("minecraft:camel=FRIENDLY");
        defaults.add("minecraft:sniffer=FRIENDLY");
        defaults.add("minecraft:armadillo=FRIENDLY");
        return defaults;
    }

    private static boolean isValidRelationEntry(String entry) {
        if (entry == null || entry.isEmpty()) {
            return false;
        }
        int sepIndex = entry.lastIndexOf('=');
        if (sepIndex <= 0 || sepIndex >= entry.length() - 1) {
            return false;
        }
        String relName = entry.substring(sepIndex + 1);
        try {
            Relation.valueOf(relName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parses a player_relations list entry into its entity ID and Relation parts.
     * @return a map entry of entity ID to Relation, or null if the entry is invalid
     */
    public static Map.Entry<String, Relation> parseRelationEntry(String entry) {
        if (entry == null || entry.isEmpty()) {
            return null;
        }
        int sepIndex = entry.lastIndexOf('=');
        if (sepIndex <= 0 || sepIndex >= entry.length() - 1) {
            return null;
        }
        String entityId = entry.substring(0, sepIndex);
        String relName = entry.substring(sepIndex + 1);
        try {
            return Map.entry(entityId, Relation.valueOf(relName));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Builds a LinkedHashMap of entity ID to Relation from the config list.
     */
    public static LinkedHashMap<String, Relation> getPlayerRelationsMap() {
        LinkedHashMap<String, Relation> map = new LinkedHashMap<>();
        for (String entry : PLAYER_RELATIONS.get()) {
            Map.Entry<String, Relation> parsed = parseRelationEntry(entry);
            if (parsed != null) {
                map.put(parsed.getKey(), parsed.getValue());
            }
        }
        return map;
    }

    /**
     * Builds a LinkedHashMap of entity type tag to Relation from the config list.
     * Strips the leading '#' from tag keys.
     */
    public static LinkedHashMap<String, Relation> getPlayerRelationTagsMap() {
        LinkedHashMap<String, Relation> map = new LinkedHashMap<>();
        for (String entry : PLAYER_RELATION_TAGS.get()) {
            Map.Entry<String, Relation> parsed = parseRelationEntry(entry);
            if (parsed != null) {
                String tagKey = parsed.getKey();
                if (tagKey.startsWith("#")) {
                    tagKey = tagKey.substring(1);
                }
                map.put(tagKey, parsed.getValue());
            }
        }
        return map;
    }

    public enum Relation {
        FRIENDLY,
        NEUTRAL,
        HOSTILE;

        public static Relation coalesce(Relation primary, Relation fallback) {
            if (primary != null) {
                return primary;
            }
            return fallback;
        }
    }

    public enum Curve {
        LINEAR,
        SQUARE,
        HALF_SQUARE
    }
}
