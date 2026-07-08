# Dinamyc Combat - Changelog

## v1.0.0-beta.43 (2026-07-08)
- Removed FirstPersonMode entirely - PAL uses defaults
- Armor always visible in all perspectives
- Third-person animations working correctly

## v1.0.0-beta.42
- Reverted to FirstPersonMode.VANILLA for armor preservation

## v1.0.0-beta.41
- Fixed axe mining when isAxeConsideredWeapon=false
- ArmorBodyFixMixin at RETURN (experimental)

## v1.0.0-beta.40
- FirstPersonMode.THIRD_PERSON_MODEL + ArmorBodyFixMixin attempt

## v1.0.0-beta.39
- FirstPersonMode.VANILLA + MirrorIfLeftHandModifier for off-hand via addModifierBefore

## v1.0.0-beta.38
- Fixed mining: only checks main hand for weapon attributes
- Animation timing: uses attack cooldown + 3 ticks
- Axe config translation added to all 14 languages

## v1.0.0-beta.37
- Fixed ArmorBodyFixMixin descriptor for submit() method

## v1.0.0-beta.36
- Fixed ArmorBodyFixMixin: targeting submit() instead of renderArmorPiece

## v1.0.0-beta.35
- ArmorBodyFixMixin targeting Minecraft HumanoidArmorLayer directly

## v1.0.0-beta.34
- Right-hand priority: only attacks when main hand has weapon
- Axe config: isAxeConsideredWeapon (default: false)
- 6 vanilla axes mapped to axe preset
- 33 weapon presets supported

## v1.0.0-beta.33
- Off-hand animation: server replaces _right with _left
- Mining prevention checks both hands

## v1.0.0-beta.32
- AvatarAnimManagerMixin target changed to AnimationStack

## v1.0.0-beta.31
- Armor visibility: AnimationStack mixin forces showArmor=true
- Off-hand weapon support in getCurrentAttack

## v1.0.0-beta.30
- MirrorIfLeftHandModifier for off-hand via addModifierBefore

## v1.0.0-beta.29
- FirstPersonConfiguration with showArmor=true
- Armor visibility fix investigation

## v1.0.0-beta.28
- Removed FirstPersonMode override (armor fix)
- Unified attack path: startAttack + onClientTick
- Removed combo HUD

## v1.0.0-beta.27
- Dual wielding support
- Combo HUD showing step/total format
- Config defaults: isMiningWithWeaponsEnabled=false, isSwingThruGrassSmart=true

## v1.0.0-beta.26
- Animation-aware attack timing

## v1.0.0-beta.25
- Mining prevention implementation
- Hold-to-attack fix
- First-person animations

## v1.0.0-beta.24
- Removed applyFirstPersonMode

## v1.0.0-beta.23
- Fixed ClientPlayerEntityMixin (attack() removed from LocalPlayer in MC 1.21.5)

## v1.0.0-beta.22
- Hold-to-attack via custom packet path
- First-person animation fix
- Mixin refmap added

## v1.0.0-beta.21
- Refmap + debug logging for mixin injection

## v1.0.0-beta.20
- Attack animations via Player Animation Library
- Combo HUD overlay
- Removed require=0, added refmap

## v1.0.0-beta.19
- Attack animation trigger implementation

## v1.0.0-beta.18
- Fixed translation key format for NeoForge ConfigurationScreen

## v1.0.0-beta.17
- Attack flow interception
- PlayerAttackProperties interface implementation
- Duplicate packet fix

## v1.0.0-beta.5 through beta.16
- Initial port to MC 1.21.5 / NeoForge 26.1.2
- Multiple mixin crash fixes
- Weapon registry sync
- Sound system
- Translation files
