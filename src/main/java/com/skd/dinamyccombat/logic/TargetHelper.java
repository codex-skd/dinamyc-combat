package com.skd.dinamyccombat.logic;

import com.skd.dinamyccombat.DinamyCombat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class TargetHelper {
    public enum Relation {
        FRIENDLY, NEUTRAL, HOSTILE;

        public static Relation coalesce(Relation value, Relation fallback) {
            if (value != null) return value;
            return fallback;
        }
    }

    public record TeamRelation(boolean areTeammates, boolean friendlyFireAllowed) {}
    public interface TeamMatcher {
        @Nullable TeamRelation getRelation(Entity attacker, Entity target);
    }

    public static Relation getRelation(Player attacker, Entity target) {
        if (attacker == target) return Relation.NEUTRAL;
        if (target instanceof TamableAnimal tameable) {
            var owner = tameable.getOwner();
            if (owner != null) return getRelation(attacker, owner);
        }
        if (target instanceof HangingEntity) return Relation.NEUTRAL;

        for (var matcher : TEAM_MATCHERS.values()) {
            var relation = matcher.getRelation(attacker, target);
            if (relation != null)
                return relation.areTeammates() ? Relation.NEUTRAL : Relation.HOSTILE;
        }

        var key = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        var id = key.toString();
        if (id != null && !id.isEmpty()) {
            var mappedRelation = RELATION_MAP.get(id);
            if (mappedRelation != null) return mappedRelation;
        }
        if (target instanceof Animal) return Relation.HOSTILE;
        if (target instanceof Enemy) return Relation.HOSTILE;
        return Relation.HOSTILE;
    }

    private static final Map<String, Relation> RELATION_MAP = new LinkedHashMap<>() {{
        put("minecraft:player", Relation.NEUTRAL);
        put("minecraft:villager", Relation.NEUTRAL);
        put("minecraft:iron_golem", Relation.NEUTRAL);
        put("minecraft:copper_golem", Relation.NEUTRAL);
    }};

    private static final Map<String, TeamMatcher> TEAM_MATCHERS = new LinkedHashMap<>();
    public static void registerTeamMatcher(String name, TeamMatcher matcher) {
        TEAM_MATCHERS.put(name, matcher);
    }

    static {
        registerTeamMatcher("vanilla", (entity1, entity2) -> {
            var team1 = entity1.getTeam();
            var team2 = entity2.getTeam();
            if (team1 == null || team2 == null) return null;
            boolean friendlyFire = team1.isAllowFriendlyFire();
            return new TeamRelation(entity1.isAlliedTo(entity2), friendlyFire);
        });
    }

    public static boolean isAttackableMount(Entity entity) {
        return entity instanceof Enemy;
    }

    public static boolean isHitAllowed(boolean isDirect, Relation relation) {
        if (isDirect) return relation != Relation.FRIENDLY;
        else return relation == Relation.HOSTILE;
    }
}
