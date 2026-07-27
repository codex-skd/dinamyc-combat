package com.skd.dinamyccombat.api;

import com.skd.dinamyccombat.api.fx.ConditionalTrailAppearance;
import com.skd.dinamyccombat.api.fx.ParticlePlacement;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class WeaponAttributes {

    public static WeaponAttributes empty() {
        return new WeaponAttributes(0, 0, null, null, false, null, null, null);
    }

    public double damage() { return 0; }

    public double speed() { return 0; }

    public String id() { return null; }

    private final double attack_range;
    private final double range_bonus;
    @Nullable private final String pose;
    @Nullable private final String off_hand_pose;
    private final Boolean two_handed;
    @Nullable private final String category;
    @Nullable private final Attack[] attacks;
    @Nullable private ConditionalTrailAppearance trail_appearance;

    public WeaponAttributes(
            double attack_range, double range_bonus,
            @Nullable String pose, @Nullable String off_hand_pose,
            Boolean isTwoHanded, String category,
            Attack[] attacks, ConditionalTrailAppearance trail_appearance) {
        this.attack_range = attack_range;
        this.range_bonus = range_bonus;
        this.pose = pose;
        this.off_hand_pose = off_hand_pose;
        this.two_handed = isTwoHanded;
        this.category = category;
        this.attacks = attacks;
        this.trail_appearance = trail_appearance;
    }

    public static final class Attack {
        private Condition[] conditions;
        private HitBoxShape hitbox;
        private double damage_multiplier = 1;
        private float movement_speed_multiplier = 1.0f;
        private float range_multiplier = 1.0f;
        private double angle = 0;
        private double upswing = 0;
        private String animation = null;
        private Sound swing_sound = null;
        private Sound impact_sound = null;
        private List<ParticlePlacement> trail_particles = List.of();

        public Attack() {}

        public Attack(Condition[] conditions, HitBoxShape hitbox, double damage_multiplier,
                      float movement_speed_multiplier, float range_multiplier,
                      double angle, double upswing, String animation,
                      Sound swing_sound, Sound impact_sound, List<ParticlePlacement> trail_particles) {
            this.conditions = conditions;
            this.hitbox = hitbox;
            this.damage_multiplier = damage_multiplier;
            this.movement_speed_multiplier = movement_speed_multiplier;
            this.range_multiplier = range_multiplier;
            this.angle = angle;
            this.upswing = upswing;
            this.animation = animation;
            this.swing_sound = swing_sound;
            this.impact_sound = impact_sound;
            this.trail_particles = trail_particles;
        }

        public static Attack empty() {
            return new Attack(null, null, 0, 0, 0, 0, 0, null, null, null, List.of());
        }

        @Nullable public Condition[] conditions() { return conditions; }
        public HitBoxShape hitbox() { return hitbox; }
        public double damageMultiplier() { return damage_multiplier; }
        public float movementSpeedMultiplier() { return movement_speed_multiplier; }
        public float rangeMultiplier() { return range_multiplier; }
        public double angle() { return angle; }
        public double upswing() { return upswing; }
        public String animation() { return animation; }
        public Sound swingSound() { return swing_sound; }
        public Sound impactSound() { return impact_sound; }
        public List<ParticlePlacement> trailParticles() { return trail_particles; }

        @Override public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Attack) obj;
            return Objects.equals(this.hitbox, that.hitbox) &&
                    Double.doubleToLongBits(this.damage_multiplier) == Double.doubleToLongBits(that.damage_multiplier) &&
                    Double.doubleToLongBits(this.angle) == Double.doubleToLongBits(that.angle) &&
                    Double.doubleToLongBits(this.upswing) == Double.doubleToLongBits(that.upswing) &&
                    Objects.equals(this.animation, that.animation) &&
                    Objects.equals(this.swing_sound, that.swing_sound);
        }
        @Override public int hashCode() {
            return Objects.hash(hitbox, damage_multiplier, angle, upswing, animation, swing_sound);
        }
        @Override public String toString() {
            return "Attack[hitbox=" + hitbox + ", damage=" + damage_multiplier + ", angle=" + angle +
                    ", upswing=" + upswing + ", animation=" + animation + "]";
        }
    }

    public enum HitBoxShape {
        FORWARD_BOX, VERTICAL_PLANE, HORIZONTAL_PLANE
    }

    public enum Condition {
        NOT_DUAL_WIELDING, DUAL_WIELDING_ANY, DUAL_WIELDING_SAME,
        DUAL_WIELDING_SAME_CATEGORY, NO_OFFHAND_ITEM, OFF_HAND_SHIELD,
        MAIN_HAND_ONLY, OFF_HAND_ONLY, MOUNTED, NOT_MOUNTED
    }

    public static final class Sound {
        private String id = null;
        private float volume = 1;
        private float pitch = 1;
        private float randomness = 0.1F;

        public Sound() {}
        public Sound(String id) { this.id = id; }

        public String id() { return id; }
        public float volume() { return volume; }
        public float pitch() { return pitch; }
        public float randomness() { return randomness; }

        @Override public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Sound) obj;
            return Objects.equals(this.id, that.id) &&
                    Float.floatToIntBits(this.volume) == Float.floatToIntBits(that.volume) &&
                    Float.floatToIntBits(this.pitch) == Float.floatToIntBits(that.pitch) &&
                    Float.floatToIntBits(this.randomness) == Float.floatToIntBits(that.randomness);
        }
        @Override public int hashCode() { return Objects.hash(id, volume, pitch, randomness); }
        @Override public String toString() {
            return "Sound[id=" + id + ", vol=" + volume + ", pitch=" + pitch + ", rand=" + randomness + "]";
        }
    }

    public double attackRange() { return attack_range; }
    public double rangeBonus() { return range_bonus; }
    @Nullable public String pose() { return pose; }
    @Nullable public String offHandPose() { return off_hand_pose; }
    @Nullable public String category() { return category; }
    public boolean isTwoHanded() { return two_handed != null ? two_handed.booleanValue() : false; }
    public Boolean two_handed() { return two_handed; }
    public Attack[] attacks() { return attacks; }
    @Nullable public ConditionalTrailAppearance trailAppearance() { return trail_appearance; }

    @Override public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WeaponAttributes) obj;
        return Double.doubleToLongBits(this.attack_range) == Double.doubleToLongBits(that.attack_range) &&
                Objects.equals(this.pose, that.pose) &&
                Objects.equals(this.two_handed, that.two_handed) &&
                Objects.equals(this.attacks, that.attacks);
    }
    @Override public int hashCode() { return Objects.hash(attack_range, two_handed, attacks); }
    @Override public String toString() {
        return "WeaponAttributes[range=" + attack_range + ", twoHanded=" + two_handed + "]";
    }
}
