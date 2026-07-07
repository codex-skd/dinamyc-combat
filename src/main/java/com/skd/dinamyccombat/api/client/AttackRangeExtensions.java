package com.skd.dinamyccombat.api.client;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttackRangeExtensions {

    public static final List<Source> sources = new ArrayList<>();

    public record Context(LivingEntity entity, Entity target) {
        public Context {
            Objects.requireNonNull(entity);
            Objects.requireNonNull(target);
        }
    }

    @FunctionalInterface
    public interface Modifier {
        double modify(Context context, double currentValue);
    }

    public record Source(Identifier id, Modifier modifier) {
        public Source {
            Objects.requireNonNull(id);
            Objects.requireNonNull(modifier);
        }
    }

    public static double getModifiedRange(LivingEntity entity, Entity target, double baseRange) {
        double range = baseRange;
        for (Source source : sources) {
            range = source.modifier().modify(new Context(entity, target), range);
        }
        return range;
    }

    public static void register(Identifier id, Modifier modifier) {
        sources.add(new Source(id, modifier));
    }
}
