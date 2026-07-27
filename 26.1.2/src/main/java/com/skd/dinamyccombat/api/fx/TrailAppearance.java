package com.skd.dinamyccombat.api.fx;

import net.minecraft.resources.Identifier;

import java.util.List;

public record TrailAppearance(List<Part> parts) {

    public record Part(
            float start,
            float end,
            Identifier texture,
            Color color,
            ParticlePlacement placement
    ) {
        public Part {
            if (start < 0.0F || start > 1.0F) {
                throw new IllegalArgumentException("Start must be between 0.0 and 1.0");
            }
            if (end < 0.0F || end > 1.0F) {
                throw new IllegalArgumentException("End must be between 0.0 and 1.0");
            }
            if (start > end) {
                throw new IllegalArgumentException("Start must be <= end");
            }
            if (texture == null) {
                throw new IllegalArgumentException("Texture must not be null");
            }
            if (color == null) {
                throw new IllegalArgumentException("Color must not be null");
            }
            if (placement == null) {
                throw new IllegalArgumentException("Placement must not be null");
            }
        }

        public static Part of(float start, float end, Identifier texture, Color color) {
            return new Part(start, end, texture, color, ParticlePlacement.DEFAULT);
        }

        public static Part of(float start, float end, Identifier texture, Color color, ParticlePlacement placement) {
            return new Part(start, end, texture, color, placement);
        }
    }

    public static final TrailAppearance EMPTY = new TrailAppearance(List.of());

    public static TrailAppearance of(Part... parts) {
        return new TrailAppearance(List.of(parts));
    }

    public boolean isEmpty() {
        return parts == null || parts.isEmpty();
    }
}
