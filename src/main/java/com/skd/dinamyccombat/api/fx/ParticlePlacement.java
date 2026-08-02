package com.skd.dinamyccombat.api.fx;

public record ParticlePlacement(
        double forwardOffset,
        double heightOffset,
        double sideOffset,
        double rotation,
        double scale
) {
    public static final ParticlePlacement DEFAULT = new ParticlePlacement(0.0, 0.0, 0.0, 0.0, 1.0);

    public ParticlePlacement {
        if (scale <= 0.0) {
            throw new IllegalArgumentException("Scale must be positive");
        }
    }

    public static ParticlePlacement of(double forwardOffset, double heightOffset, double sideOffset) {
        return new ParticlePlacement(forwardOffset, heightOffset, sideOffset, 0.0, 1.0);
    }

    public ParticlePlacement withRotation(double rotation) {
        return new ParticlePlacement(forwardOffset, heightOffset, sideOffset, rotation, scale);
    }

    public ParticlePlacement withScale(double scale) {
        return new ParticlePlacement(forwardOffset, heightOffset, sideOffset, rotation, scale);
    }
}
