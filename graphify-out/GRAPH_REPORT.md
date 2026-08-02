# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 179 files · ~168,607 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 797 nodes · 1484 edges · 41 communities (38 shown, 3 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 21 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e31ca1da`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- WeaponAttributes
- Packets.java
- LivingEntityMixin.java
- Attack
- PlayerAttackHelper
- DinamycCombat.java
- ClientNetwork.java
- ClientPlayerEntityMixin
- DinamycCombatNeoForge.java
- ServerConfig
- PlatformHelper
- SlashParticleEffect
- EnchantmentMixin.java
- ClientPlayerInteractionManagerMixin.java
- ParticlePlacement
- ItemConditions
- CurseForge — Variables del proyecto
- Publisher
- BetterCombatMixinPlugin
- Flujo de trabajo — Dinamyc Combat (NeoForge)
- MinecraftClient_DinamycCombat
- ItemStackTooltipMixin.java
- AttackRangeExtensions.java
- InGameHudInject.java
- AbstractClientPlayerEntityMixin
- PlayerEntityRangeMixin.java
- MinecraftClientAccessor
- Dinamyc Combat
- MathHelper
- [0.0.0-beta.1] - 2026-08-02
- CLAUDE.md — dinamyc_combat (26.2)
- gradlew
- PlatformClientHelper.java
- AttributeModifierHelper.java
- BetterCombatDataComponents.java
- EntityAttributeHelper

## God Nodes (most connected - your core abstractions)
1. `WeaponAttributes` - 43 edges
2. `Attack` - 27 edges
3. `PlayerAttackHelper` - 18 edges
4. `SlashParticleEffect` - 18 edges
5. `WeaponRegistry` - 17 edges
6. `Condition` - 16 edges
7. `ClientPlayerEntityMixin` - 16 edges
8. `AttackAnimation` - 16 edges
9. `ParticlePlacement` - 15 edges
10. `ServerConfig` - 15 edges

## Surprising Connections (you probably didn't know these)
- `AttackHand` --references--> `Attack`  [EXTRACTED]
  src/main/java/com/skd/dinamyccombat/api/AttackHand.java → src/main/java/com/skd/dinamyccombat/api/WeaponAttributes.java
- `AttackHand` --references--> `WeaponAttributes`  [EXTRACTED]
  src/main/java/com/skd/dinamyccombat/api/AttackHand.java → src/main/java/com/skd/dinamyccombat/api/WeaponAttributes.java
- `Attack` --references--> `ParticlePlacement`  [EXTRACTED]
  src/main/java/com/skd/dinamyccombat/api/WeaponAttributes.java → src/main/java/com/skd/dinamyccombat/api/fx/ParticlePlacement.java
- `AttackSelection` --references--> `Attack`  [EXTRACTED]
  src/main/java/com/skd/dinamyccombat/logic/PlayerAttackHelper.java → src/main/java/com/skd/dinamyccombat/api/WeaponAttributes.java
- `ConditionalTrailAppearance` --references--> `TrailAppearance`  [EXTRACTED]
  src/main/java/com/skd/dinamyccombat/api/fx/ConditionalTrailAppearance.java → src/main/java/com/skd/dinamyccombat/api/fx/TrailAppearance.java

## Import Cycles
- None detected.

## Communities (41 total, 3 thin omitted)

### Community 0 - "WeaponAttributes"
Cohesion: 0.06
Nodes (23): DataComponentPatch, ResourceManager, Shadow, AttributesContainer, ConditionalTrailAppearance, Identifier, Nullable, WeaponAttributes (+15 more)

### Community 1 - "Packets.java"
Cohesion: 0.12
Nodes (19): CustomPacketPayload, Ack, AttackAnimation, AttackSound, C2S_AttackRequest, C2S_BlockHit, ConfigSync, BlockPos (+11 more)

### Community 2 - "LivingEntityMixin.java"
Cohesion: 0.06
Nodes (34): Inventory, NonNullList, Redirect, ServerGamePacketListenerImpl, InventoryUtil, ItemStack, Player, ConfigurableKnockback (+26 more)

### Community 3 - "Attack"
Cohesion: 0.06
Nodes (21): Attack, Condition, DUAL_WIELDING_ANY, DUAL_WIELDING_SAME, DUAL_WIELDING_SAME_CATEGORY, MAIN_HAND_ONLY, MOUNTED, NO_OFFHAND_ITEM (+13 more)

### Community 4 - "PlayerAttackHelper"
Cohesion: 0.09
Nodes (12): AttackHand, ItemStack, ComboState, EntityPlayer_DinamycCombat, AttackSelection, ItemStack, Nullable, Player (+4 more)

### Community 5 - "DinamycCombat.java"
Cohesion: 0.17
Nodes (12): FTBTeamsCompat, coalesce(), Entity, Nullable, Player, Relation, FRIENDLY, HOSTILE (+4 more)

### Community 6 - "ClientNetwork.java"
Cohesion: 0.07
Nodes (26): AnimationController, AnimationData, AnimationSetter, Avatar, FirstPersonConfiguration, FirstPersonMode, PlayerAnimationController, PlayState (+18 more)

### Community 7 - "ClientPlayerEntityMixin"
Cohesion: 0.09
Nodes (9): EntityHitResult, ClientPlayerAttackProperties, ClientPlayerEntityMixin, CallbackInfo, Inject, LocalPlayer, Mixin, Override (+1 more)

### Community 8 - "DinamycCombatNeoForge.java"
Cohesion: 0.15
Nodes (12): AnimatedHand, MAIN_HAND, OFF_HAND, TWO_HANDED, from(), Accessor, Mixin, LivingEntityAccessor (+4 more)

### Community 9 - "ServerConfig"
Cohesion: 0.09
Nodes (24): HitResult, coalesce(), Curve, HALF_SQUARE, LINEAR, SQUARE, BooleanValue, Builder (+16 more)

### Community 10 - "PlatformHelper"
Cohesion: 0.11
Nodes (14): Identifier, PlayerAttachments, DinamycCombatPlayerAttachments, AttachmentType, DeferredRegister, Player, CustomPacketPayload, FriendlyByteBuf (+6 more)

### Community 11 - "SlashParticleEffect"
Cohesion: 0.11
Nodes (16): Codec, MapCodec, ParticleOptions, Identifier, RegistryFriendlyByteBuf, StreamCodec, WeaponAttributesIdComponent, BetterCombatParticles (+8 more)

### Community 12 - "EnchantmentMixin.java"
Cohesion: 0.11
Nodes (16): PlayerAttackProperties, EnchantmentMixin, CallbackInfoReturnable, EquipmentSlot, Inject, ItemStack, LivingEntity, Mixin (+8 more)

### Community 13 - "ClientPlayerInteractionManagerMixin.java"
Cohesion: 0.10
Nodes (23): Direction, ClientConfig, BooleanValue, Builder, ConfigValue, DoubleValue, EnumValue, IntValue (+15 more)

### Community 14 - "ParticlePlacement"
Cohesion: 0.12
Nodes (6): Color, ParticlePlacement, Identifier, Part, TrailAppearance, TrailConfig

### Community 15 - "ItemConditions"
Cohesion: 0.17
Nodes (8): Item, ItemConditions, Identifier, ItemStack, CompatibilitySpecifier, FallbackConfig, WeaponAttributesFallback, PatternMatching

### Community 16 - "CurseForge — Variables del proyecto"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.2, Parámetros del upload (+7 more)

### Community 17 - "Publisher"
Cohesion: 0.23
Nodes (7): AttackHit, AttackStart, DinamycCombatClientEvents, InteractionHand, LivingEntity, Player, Publisher

### Community 18 - "BetterCombatMixinPlugin"
Cohesion: 0.11
Nodes (14): ClassNode, EventBusSubscriber, IMixinConfigPlugin, IMixinInfo, PlayerLoggedInEvent, ServerAboutToStartEvent, CompatFeatures, DinamycCombat (+6 more)

### Community 19 - "Flujo de trabajo — Dinamyc Combat (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Dinamyc Combat (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 20 - "MinecraftClient_DinamycCombat"
Cohesion: 0.21
Nodes (3): Entity, Nullable, MinecraftClient_DinamycCombat

### Community 21 - "ItemStackTooltipMixin.java"
Cohesion: 0.29
Nodes (9): Component, ItemStackTooltipMixin, CallbackInfoReturnable, Inject, ItemStack, Mixin, Player, TooltipContext (+1 more)

### Community 22 - "AttackRangeExtensions.java"
Cohesion: 0.35
Nodes (8): FunctionalInterface, AttackRangeExtensions, Context, Entity, Identifier, LivingEntity, Modifier, Source

### Community 23 - "InGameHudInject.java"
Cohesion: 0.33
Nodes (7): DeltaTracker, InGameHudInject, CallbackInfo, Inject, Minecraft, Mixin, Unique

### Community 24 - "AbstractClientPlayerEntityMixin"
Cohesion: 0.33
Nodes (5): AbstractClientPlayerEntityMixin, CallbackInfoReturnable, Inject, Mixin, Unique

### Community 25 - "PlayerEntityRangeMixin.java"
Cohesion: 0.39
Nodes (7): Operation, Attribute, Holder, Mixin, Player, PlayerEntityRangeMixin, WrapOperation

### Community 26 - "MinecraftClientAccessor"
Cohesion: 0.43
Nodes (3): Accessor, Mixin, MinecraftClientAccessor

### Community 27 - "Dinamyc Combat"
Cohesion: 0.29
Nodes (6): Build, Dependencies, Dinamyc Combat, Features, Requirements, Version: 0.0.0-beta.1

### Community 29 - "[0.0.0-beta.1] - 2026-08-02"
Cohesion: 0.29
Nodes (6): [0.0.0-beta.1] - 2026-08-02, [1.0.0] - 2026-08-02, Added, Added, Changed, Dinamyc Combat - Changelog

### Community 30 - "CLAUDE.md — dinamyc_combat (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — dinamyc_combat (26.2), Prioridad de instrucciones, Workflow del mod

### Community 31 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 37 - "AttributeModifierHelper.java"
Cohesion: 0.47
Nodes (6): AttributeModifier, Multimap, AttributeModifierHelper, Attribute, Holder, ItemStack

### Community 38 - "BetterCombatDataComponents.java"
Cohesion: 0.36
Nodes (6): DataComponentType, DeferredHolder, BetterCombatDataComponents, DeferredRegister, Identifier, IEventBus

## Knowledge Gaps
- **62 isolated node(s):** `FORWARD_BOX`, `VERTICAL_PLANE`, `HORIZONTAL_PLANE`, `NOT_DUAL_WIELDING`, `DUAL_WIELDING_ANY` (+57 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `WeaponAttributes` connect `WeaponAttributes` to `Attack`, `PlayerAttackHelper`?**
  _High betweenness centrality (0.132) - this node is a cross-community bridge._
- **Why does `Attack` connect `Attack` to `WeaponAttributes`, `PlayerAttackHelper`, `MinecraftClient_DinamycCombat`, `ParticlePlacement`?**
  _High betweenness centrality (0.053) - this node is a cross-community bridge._
- **Why does `AttackHand` connect `PlayerAttackHelper` to `WeaponAttributes`, `Attack`, `MinecraftClient_DinamycCombat`?**
  _High betweenness centrality (0.045) - this node is a cross-community bridge._
- **What connects `FORWARD_BOX`, `VERTICAL_PLANE`, `HORIZONTAL_PLANE` to the rest of the system?**
  _62 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `WeaponAttributes` be split into smaller, more focused modules?**
  _Cohesion score 0.06265984654731457 - nodes in this community are weakly interconnected._
- **Should `Packets.java` be split into smaller, more focused modules?**
  _Cohesion score 0.11980676328502415 - nodes in this community are weakly interconnected._
- **Should `LivingEntityMixin.java` be split into smaller, more focused modules?**
  _Cohesion score 0.06458635703918723 - nodes in this community are weakly interconnected._