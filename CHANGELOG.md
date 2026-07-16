# Dinamyc Combat - Changelog

## 0.0.0-beta.79 (2026-07-15)
- fix: revert triggerAnimation con float, usar SpeedModifier en su lugar
- feat: SpeedModifier escala la velocidad según attack speed del arma
- fix: eliminar SpeedModifier previo antes de añadir nuevo (evita acumulación)

## 0.0.0-beta.78 (2026-07-15)
- feat: velocidad de animación según la velocidad de ataque del arma
- feat: animaciones más rápidas para armas rápidas (espadas), más lentas para armas lentas (hachas)
- refactor: usar `triggerAnimation(animId, speed)` de PAC para control de velocidad

## 0.0.0-beta.77 (2026-07-14)
- feat: both arms visible in THIRD_PERSON_MODEL with correct animations
- fix: enable showLeftArm and showLeftItem for full dual-wielding first-person view
- Removed debug mixins (cleanup)

## 0.0.0-beta.76 (2026-07-14)
- feat: switched to THIRD_PERSON_MODEL — full player model renders in first person
- feat: right arm, right item, and armor visible in first person (left arm/item hidden)
- fix: first-person arm swing now matches third-person animation exactly

## 0.0.0-beta.75 (2026-07-14)
- fix: debug logs reveal right_arm yaw never reaches JSON keyframe values (peaks at 0.537 vs expected 0.956)
- fix: weapon never swings left because yaw never becomes negative
- refactor: switched from HANDS_ONLY_ARM + custom mixin to PAC HANDS_ONLY mode
- feat: PAC applies original Better Combat right_item transforms (position+rotation pre-baked in JSONs)
- chore: removed armRotationScale config (not used by HANDS_ONLY mode)
- feat: upgraded to PAC beta.20 (built-in hand position from arm rotation)
- refactor: removed custom FirstPersonItemRendererMixin entirely
- chore: switched to FirstPersonMode.HANDS_ONLY_ARM with setArmRotationScale(1.0f)
- chore: PAC now handles all first-person transforms (rotation + position from arm data)

## 0.0.0-beta.73 (2026-07-14)
- fix: remove arm bone position from first-person mixin (was applying shoulder model-space coords)
- fix: compute hand position from rotation only: sin(yaw)*arm_length for X, sin(pitch)*arm_length*0.6 for Y
- fix: weapon now swings freely left-right and up-down without being anchored by shoulder position

## 0.0.0-beta.72 (2026-07-14)
- feat: apply arm bone position (x/y/z) in FirstPersonItemRendererMixin alongside rotation
- feat: add tick 0 rightArm/leftArm keyframes to all animation JSONs for smooth transitions
- fix: first-person weapon now completes full swing arc matching third-person arm motion
- docs: add WORKFLOW.md with development conventions
- chore: align version format to 0.0.0-beta.X in gradle.properties
- feat: add curseforge/ files for project description and version release notes

## v1.0.0-beta.71 (2026-07-13)
- Added tick 0 rightArm/leftArm keyframes to all animation JSONs (smooth transition from default pose)
- FirstPersonItemRendererMixin now applies arm bone POSITION (x/y/z) in addition to rotation
- Position captures the shoulder movement during the swing, completing the full hand arc
- First-person weapon now matches third-person arm swing: rotation + shoulder position together reproduce the full motion

## v1.0.0-beta.71 (2026-07-13)
- Custom FirstPersonItemRendererMixin: computes item position+rotation from right_arm bone at runtime
- Position derived from arm rotation (sin(yaw)*arm_length for hand arc) — item swings through space like the hand
- Rotation applied directly from arm bone (same as HANDS_ONLY_ARM)
- PAC set to NONE mode — our mixin handles all first-person transforms
- JSON right_item data kept as small deltas for correct third-person rendering only
- First-person weapon now moves in a visible arc matching the third-person arm swing

## v1.0.0-beta.70 (2026-07-13)
- Switched to HANDS_ONLY_ARM with setArmRotationScale(1.0f) — PAC applies arm rotation directly to item (no position offset)
- Restored original Better Combat right_item deltas for third-person use only
- First person: item rotates with the arm at the hand pivot (like Better Combat), no position displacement
- PAC beta.19's configurable armRotationScale allows tuning the rotation intensity

## v1.0.0-beta.69 (2026-07-12)
- Back to HANDS_ONLY mode with generated right_item keyframes from arm rotation data
- right_item now has position values derived from arm yaw/pitch/roll (hand arc motion)
- Added tick 0 and tick 15 right_item keyframes for smooth animation start/recovery
- First person: only item visible, no arms, but item moves through space like the hand in third person
- Removed generated scripts

## v1.0.0-beta.68 (2026-07-12)
- Switched to THIRD_PERSON_MODEL — full player model renders in first person
- Right arm, right item, and armor visible in first person (left arm/item hidden)
- First-person arm swing now matches third-person animation exactly

## v1.0.0-beta.67 (2026-07-11)
- Switched from HANDS_ONLY_ARM to HANDS_ONLY mode — PAC now applies right_item transforms (position + rotation) instead of right_arm rotation alone
- First-person item now moves through space like the hand in third person (positional translation + rotation)
- Removed armRotationScale config (not used by HANDS_ONLY mode)

## v1.0.0-beta.66 (2026-07-11)
- Updated PAC dependency to beta.19 (configurable armRotationScale via FirstPersonConfiguration)
- Switched to FirstPersonMode.HANDS_ONLY_ARM with setArmRotationScale(1.0f) — PAC handles first-person natively
- Removed custom FirstPersonItemRendererMixin (no longer needed, PAC does it)
- All first-person animation logic delegated to PAC

## v1.0.0-beta.65 (2026-07-11)
- Fixed third-person weapon positioning: restored right_item/left_item keyframes from original Better Combat data
- Fixed first-person animation too weak: restored custom FirstPersonItemRendererMixin with SWING_SCALE=1.0f (full arm rotation)
- Disabled PAL's first-person handling (FirstPersonMode.NONE) — our mixin handles first-person item transforms
- Simplified FirstPersonConfiguration (removed showRightArm/showLeftArm/showRightItem options, not needed with NONE mode)

## v1.0.0-beta.64 (2026-07-11)
- Updated PAL dependency to beta.18 (HANDS_ONLY_ARM mode built-in with arm rotation scaling)
- Changed FirstPersonMode to HANDS_ONLY_ARM — PAL now applies right_arm rotation (scaled 0.2x) directly
- Removed custom FirstPersonItemRendererMixin (replaced by PAL's built-in HANDS_ONLY_ARM)
- Removed ArmorVisibilityMixin and FirstPersonConfigurationMixin (no longer needed)
- Cleaned up .gitignore (com/, graphify-out/, nul)
- Cleaned up unused files (nul, pal_feature_request.txt)

## v1.0.0-beta.63 (2026-07-11)
- Replaced PAL HANDS_ONLY item transform with custom mixin using rightArm rotation (scaled 0.2x)
- Removed all right_item data from animation JSONs (PAL's HANDS_ONLY now no-ops)
- First-person item now follows arm swing rotation from third-person animation, scaled for hand pivot

## v1.0.0-beta.62 (2026-07-11)
- Restored original Better Combat rightItem values for first-person animations
- Added tick 15 keyframes with interpolated recovery (previously only ticks 8→11)
- First-person item animation now uses values designed for first-person space, not arm model values

## v1.0.0-beta.61 (2026-07-11)
- Fixed weapon position during first-person animations: right_item now only copies rotation (pitch/yaw/roll) from rightArm, NOT position (x/y/z)
- Weapons stay at correct vanilla first-person position while rotating with the arm swing

## v1.0.0-beta.60 (2026-07-11)
- Fixed first-person animations: right_item bone data now mirrors rightArm at all keyframe ticks
- Removed standalone/old right_item entries that had different values from the arm animation
- PAL swapTheZYAxis converts arm-space values to item-space during loading
- First-person item motion now matches the third-person arm swing exactly

## v1.0.0-beta.59 (2026-07-11)
- Extended right_item bone keyframes in 13 animation JSONs: added tick 15 keyframes with interpolated recovery values
- First-person animations now last from tick 8 through tick 15 (previously only tick 8→11)
- Updated PAL dependency to beta.17 for HANDS_ONLY mode

## v1.0.0-beta.58 (2026-07-11)
- Updated PAL dependency to beta.17 (new HANDS_ONLY first-person mode implemented)
- FirstPersonMode changed back to HANDS_ONLY — vanilla first-person view preserved, item bone transforms applied during attacks
- Animation speed scaling removed (caused exponential compound and invisible animations)
- ShowRightArm=false, ShowLeftArm=false — no third-person arm model rendered in first person

## v1.0.0-beta.57 (2026-07-11)
- Removed animation speed scaling completely (SpeedModifier caused exponential speed compound and animations too fast to see)
- Changed first-person mode to VANILLA — first person looks exactly like vanilla Minecraft (no arm models, no floating items)
- Third-person animations continue working with full arm/body/item animations

## v1.0.0-beta.56 (2026-07-11)
- Animation speed scaling: animations automatically speed up/slow down based on weapon attack cooldown (via PAL SpeedModifier)
- Reverted to THIRD_PERSON_MODEL for first-person (HANDS_ONLY not compiled in PAL beta.16, caused invisible animations)
  - Left arm and left item remain hidden (showLeftArm=false, showLeftItem=false)
  - Only right arm + right item visible in first person

## v1.0.0-beta.55 (2026-07-11)
- Removed animation gate in MinecraftClientInject.onStartAttack: single clicks always go through, no longer blocked by ongoing animation
- Removed hasAnimationFinished() gate in handleAttackAnimation: every attack triggers animation, PAL handles blending automatically
- Cleaned up unused shadows and imports in MinecraftClientInject

## v1.0.0-beta.54 (2026-07-11)
- Animation timing delegated to PAL: isAnimationActive() now queries controller.hasAnimationFinished() instead of custom tick tracking
- Removed custom animEndTick/animationActive fields from ClientPlayerEntityMixin
- Fixed bone naming in 28 animation JSONs: rightItem → right_item, leftItem → left_item (PAL uses snake_case)
- Added ClientNetwork.isAttackAnimationPlaying() helper method

## v1.0.0-beta.53 (2026-07-10)
- Updated PAL dependency to beta.16 (new HANDS_ONLY first-person mode)
- Changed FirstPersonMode from THIRD_PERSON_MODEL to HANDS_ONLY
  - Vanilla first-person view preserved (no extra arms, no full body rendering)
  - PAL applies bone transforms directly to the item in hand via ItemInHandLayerMixin
  - Left arm stays hidden (showLeftArm=false)
- Compatible with V&V (no HumanoidArmorLayer mixin conflict)

## v1.0.0-beta.52 (2026-07-10)
- Fixed double animation: removed player.swing() from custom attack code paths (vanilla arm swing no longer conflicts with PAL animations)
- Single click now delegates to vanilla startAttack (basic Minecraft attack)
- Hold-to-attack uses custom animation without vanilla swing interference
- FP_CONFIG: left arm hidden in first person (showLeftArm=false, showLeftItem=false)
- Cleaned up MinecraftClientInject imports

## v1.0.0-beta.51 (2026-07-10)
- Updated PAL dependency to beta.15 (fixes: hasAnimationFinished for first trigger, ItemInHandRenderer no longer requires isActive(), armor always visible in first person)
- Kept FirstPersonConfigurationMixin as safety net (harmless, PAL no longer checks isShowArmor)

## v1.0.0-beta.50 (2026-07-10)
- New approach: FirstPersonConfigurationMixin injects into PAL's FirstPersonConfiguration.isShowArmor() to force return true
- No longer injects into HumanoidArmorLayer at all — avoids double-mixin conflict with PAL
- V&V crash fixed (confirmed: beta.49 loaded without crash)
- Armor visibility restored in first person

## v1.0.0-beta.49 (2026-07-10)
- Removed ArmorVisibilityMixin to avoid double-mixin conflict with player_animation_core's HumanoidArmorLayerMixin on the same injection point
- PAL's existing mixin handles armor visibility via isShowArmor() — our priority 9999 safety net removed
- Potential fix for V&V MixinTransformerError crash during RegisterRenderers

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
