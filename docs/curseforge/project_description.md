# Dinamyc Combat

A combat overhaul mod for Minecraft NeoForge that brings Better Combat-style weapon animations to 1.21.5+.

## Features

- **33 weapon presets** — Each with unique dual-handed and one-handed animation sets (slashes, stabs, slams, uppercuts, spins, and more)
- **Hold-to-attack** — Hold click for continuous attacks with alternating hands for dual wielding
- **Off-hand support** — Left-handed animations via MirrorIfLeftHandModifier
- **First-person weapon animation** — Weapons animate in first person matching the third-person arm swing, without showing arms or player model
- **Third-person weapon positioning** — Weapons correctly follow the arm swing in third person
- **Armor always visible** in all perspectives
- **Configurable** — Client-side config for first-person mode and animation preferences

## Requirements

- **NeoForge 26.1.2+** (MC 1.21.5)
- **Player Animation Core (PAC) beta.19+** (included in the jar)

## Compatibility

- Compatible with Vampires & Vampires (no mixin conflicts)
- Works with all vanilla weapons and any modded weapons registered as swords, axes, pickaxes, shovels, or hoes
- Fully clientside — no server-side installation required

## Weapon Presets

Each preset defines a unique attack pattern with specific animations:

| Category | Presets |
|----------|---------|
| **One-handed** | Slash horizontal (left/right), Slash switch blade (left/right), Stab, Stab mounted, Swipe horizontal, Uppercut, Slam, Slam instant, Punch |
| **Two-handed** | Slash horizontal (left/right), Slash vertical (left/right), Stab (left/right), Slam, Slam heavy, Spin |
| **Dual-handed** (dual wielding) | Slash cross, Slash uncross, Stab |
| **Poses** | One-handed backwards, Two-handed bow/crossbow/heavy/katana/polearm/scythe/sword |

Attack animations trigger automatically based on the weapon type and attack speed.
