# Dinamyc Combat - Changelog

Todas las versiones notables de Dinamyc Combat (26.2) están documentadas aquí.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Para el historial completo de la versión 26.1.2, ver `dinamyc_combat/26.1.2/CHANGELOG.md` (rama `minecraft/26.1.2/neoforge-26.1.2.78/production`).
---

## [1.0.4] - 2026-08-18

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `dinamyc_combat-26.2-neoforge-26.2.0.45-beta-1.0.4.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_DINAMYC_COMBAT_26-2.md` para reflejar la nueva rama de trabajo.

---

## [1.0.3] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `dinamyc_combat-26.2-neoforge-26.2.0.37-beta-1.0.3.jar` (se incorpora la versión de cargador/NeoForge en el nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.


## [1.0.2] - 2026-08-05

### Change

- **Recompilado contra NeoForge `26.2.0.37-beta`**: bump de `neo_version` en `gradle.properties` (`26.2.0.32-beta` -> `26.2.0.37-beta`). Verificado con `runServer` (arranque sin errores).

## [1.0.1] - 2026-08-02

### Refactor
- Clases residuales del fork "Better Combat" renombradas a naming propio: `BetterCombatDataComponents` → `DinamycDataComponents`, `BetterCombatMixinPlugin` → `DinamycMixinPlugin`, `BetterCombatParticles` → `DinamycParticles`, y método `setKnockbackMultiplier_BetterCombat` → `setKnockbackMultiplier_Dinamyc`. Sin cambios de gameplay.

## [1.0.0] - 2026-08-02
