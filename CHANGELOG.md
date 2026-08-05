# Dinamyc Combat - Changelog

Todas las versiones notables de Dinamyc Combat (26.2) están documentadas aquí.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Para el historial completo de la versión 26.1.2, ver `dinamyc_combat/26.1.2/CHANGELOG.md` (rama `minecraft/26.1.2/neoforge-26.1.2.78/production`).
---

## [1.0.2] - 2026-08-05

### Change

- **Recompilado contra NeoForge `26.2.0.37-beta`**: bump de `neo_version` en `gradle.properties` (`26.2.0.32-beta` -> `26.2.0.37-beta`). Verificado con `runServer` (arranque sin errores).

## [1.0.1] - 2026-08-02

### Refactor
- Clases residuales del fork "Better Combat" renombradas a naming propio: `BetterCombatDataComponents` → `DinamycDataComponents`, `BetterCombatMixinPlugin` → `DinamycMixinPlugin`, `BetterCombatParticles` → `DinamycParticles`, y método `setKnockbackMultiplier_BetterCombat` → `setKnockbackMultiplier_Dinamyc`. Sin cambios de gameplay.

## [1.0.0] - 2026-08-02

### Added
- Primera versión estable de Dinamyc Combat para Minecraft 26.2 / NeoForge 26.2.0.32-beta, partiendo del port inicial beta.1.

## [0.0.0-beta.1] - 2026-08-02

### Added
- Port inicial a Minecraft 26.2 / NeoForge 26.2.0.32-beta, partiendo del estado estable de la versión 26.1.2 (`1.0.5`): 33 presets de armas, hold-to-attack, dual wield, animaciones en primera persona y pantalla de configuración.
- Dependencias actualizadas a 26.2: `player_animation_core` `0.0.0-beta.1` y Cloth Config `26.2.155`.

### Changed
- `EntityHitboxDebugRendererMixin` eliminado: su target `LevelRenderer.renderHitbox` ya no existe en 26.2 (el mixin era un stub vacío).
- `RangedWeaponItemMixin` reorientado: `ProjectileWeaponItem.getProjectile` fue renombrado a `getHeldProjectile` (estático) en 26.2; el override de off-hand para jugadores se mantiene con `@Inject` en HEAD.
- `PlayerEntityRangeMixin`: target actualizado de `Player.getEntityInteractionRange()` a `entityInteractionRange()` (renombrado en 26.2).
