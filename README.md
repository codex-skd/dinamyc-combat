# Dinamyc Combat

> This mod is based on **Better Combat** by Daedelus. Not affiliated with or endorsed by the original author.

Better Combat-style weapon animations for NeoForge — 33 weapon presets with full first-person support. Dinamyc Combat overhauls Minecraft's combat system with hand-crafted weapon animations: swing, stab, slam, and spin your way through enemies, each preset with its own attack pattern.

## Features

- **33 weapon presets**: One-handed (slash horizontal/vertical, stab, stab mounted, swipe, uppercut, slam, punch), two-handed (slash, stab, slam, spin), dual-handed (cross slash, uncross slash, stab) and pose presets (one-handed backwards, two-handed bow/crossbow/heavy/katana/polearm/scythe/sword).
- **Hold-to-attack**: Hold click for continuous attacks, with alternating hands for dual wielding.
- **Off-hand support**: Left-handed animations via `MirrorIfLeftHandModifier` — weapons in the off-hand automatically mirror their animations.
- **First-person animation**: Full player model renders in first person, showing both arms, armor, and weapons with the same animations as third person.
- **Armor always visible**: Armor renders correctly in all perspectives.
- **Configurable**: Full client-side configuration screen (Mods → Dinamyc Combat → Config).

## Requirements

- Minecraft 26.1.2
- NeoForge 26.1.2.78+

## Build

```bash
gradlew build
```

The JAR is generated at `build/libs/dinamyc_combat-26.1.2-neoforge-<version>.jar`.
