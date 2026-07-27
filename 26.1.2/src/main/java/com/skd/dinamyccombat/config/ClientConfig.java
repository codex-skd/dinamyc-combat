package com.skd.dinamyccombat.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue IS_HOLD_TO_ATTACK_ENABLED = BUILDER
            .comment("Hold down the attack button to repeatedly attack when the weapon is ready",
                    "If disabled, you need to click for each attack")
            .define("isHoldToAttackEnabled", true);

    public static final ModConfigSpec.BooleanValue IS_MINING_WITH_WEAPONS_ENABLED = BUILDER
            .comment("Allow mining blocks with weapons")
            .define("isMiningWithWeaponsEnabled", false);

    public static final ModConfigSpec.BooleanValue IS_SWING_THRU_GRASS_ENABLED = BUILDER
            .comment("Allow attacking through grass, flowers, and other non-solid plants")
            .define("isSwingThruGrassEnabled", true);

    public static final ModConfigSpec.BooleanValue IS_SWING_THRU_GRASS_SMART = BUILDER
            .comment("Only swing through grass when targeting an entity behind it")
            .define("isSwingThruGrassSmart", true);

    public static final ModConfigSpec.BooleanValue IS_ATTACK_INSTEAD_OF_MINE_WHEN_ENEMIES_CLOSE_ENABLED = BUILDER
            .comment("When enemies are close, attacking will target them instead of mining blocks")
            .define("isAttackInsteadOfMineWhenEnemiesCloseEnabled", true);

    public static final ModConfigSpec.BooleanValue IS_HIGHLIGHT_CROSSHAIR_ENABLED = BUILDER
            .comment("Show highlight on the crosshair when targeting an entity within attack range")
            .define("isHighlightCrosshairEnabled", true);

    public static final ModConfigSpec.IntValue HUD_HIGHLIGHT_COLOR = BUILDER
            .comment("Color of the crosshair highlight in ARGB format",
                    "Default is red (0xFFFF0000)")
            .defineInRange("hudHighlightColor", 0xFFFF0000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue IS_SHOWING_SWEEP_EFFECT = BUILDER
            .comment("Show sweep arc effect during attacks")
            .define("isShowingSweepEffect", true);

    public static final ModConfigSpec.BooleanValue IS_SHOWING_ARMS_IN_FIRST_PERSON = BUILDER
            .comment("Show arms in first person view during attack animations")
            .define("isShowingArmsInFirstPerson", true);

    public static final ModConfigSpec.BooleanValue IS_SHOWING_OTHER_HAND_FIRST_PERSON = BUILDER
            .comment("Show the other hand (off-hand or two-handed) in first person view during attack animations")
            .define("isShowingOtherHandFirstPerson", true);

    public static final ModConfigSpec.BooleanValue IS_SWEEPING_PARTICLE_ENABLED = BUILDER
            .comment("Show vanilla-style sweeping attack particles")
            .define("isSweepingParticleEnabled", true);

    public static final ModConfigSpec.BooleanValue IS_TOOLTIP_ATTACK_RANGE_ENABLED = BUILDER
            .comment("Show attack range in weapon tooltips")
            .define("isTooltipAttackRangeEnabled", true);

    public static final ModConfigSpec.BooleanValue IS_TOOLTIP_ATTACK_RANGE_REFORMAT = BUILDER
            .comment("Reformat the tooltip attack speed line to be more readable")
            .define("isTooltipAttackRangeReformat", false);

    public static final ModConfigSpec.IntValue WEAPON_SWING_SOUND_VOLUME = BUILDER
            .comment("Volume of weapon swing sounds (0-100)",
                    "0 = off, 100 = full volume")
            .defineInRange("weaponSwingSoundVolume", 100, 0, 100);

    public static final ModConfigSpec.BooleanValue IS_DEBUG_OBB_ENABLED = BUILDER
            .comment("Show debug oriented bounding boxes for attack hitboxes",
                    "Requires F3 debug screen to be open")
            .define("isDebugOBBEnabled", false);

    public static final ModConfigSpec.ConfigValue<String> SWING_THRU_GRASS_BLACKLIST = BUILDER
            .comment("Block IDs that should NOT be swung through (comma-separated)")
            .define("swingThruGrassBlacklist", "");

    public static final ModConfigSpec.ConfigValue<String> MINE_WITH_WEAPON_BLACKLIST = BUILDER
            .comment("Block IDs that weapons should NOT be able to mine (comma-separated)")
            .define("mineWithWeaponBlacklist", "");

    public static final ModConfigSpec.ConfigValue<String> MINE_WITH_WEAPON_WHITELIST = BUILDER
            .comment("Block IDs that weapons should be able to mine (comma-separated)",
                    "Overrides the blacklist when specified")
            .define("mineWithWeaponWhitelist", "");

    public static final ModConfigSpec.EnumValue<TriStateAuto> FIRST_PERSON_ANIMATIONS = BUILDER
            .comment("Control first-person attack animations",
                    "YES = always show, NO = never show, AUTO = follows server config")
            .defineEnum("firstPersonAnimations", TriStateAuto.AUTO);

    public static final ModConfigSpec.DoubleValue LEG_ANIMATION_THRESHOLD = BUILDER
            .comment("Minimum movement speed threshold before leg animations activate",
                    "Lower values mean legs animate at slower speeds")
            .defineInRange("legAnimationThreshold", 0.5, 0.0, 5.0);

    public static final ModConfigSpec.BooleanValue IS_AXE_CONSIDERED_WEAPON = BUILDER
            .comment("Treat axes as weapons for combat animations",
                    "When enabled, axes will use weapon attack animations like swords")
            .define("isAxeConsideredWeapon", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
