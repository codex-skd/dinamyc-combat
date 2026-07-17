package com.skd.dinamyccombat.client.effect;

import net.minecraft.util.Mth;

public enum SwingArc {
    SLASH_HORIZONTAL(true,  -1.2f,  1.2f, 0.6f),
    SLASH_VERTICAL(false,  -0.8f,  0.8f, 0.6f),
    STAB(true,              0.0f,  1.0f, 0.3f),
    SLAM(false,            -1.0f,  0.4f, 0.5f),
    SPIN(true,             -3.0f,  3.0f, 0.8f),
    SWIPE_HORIZONTAL(true,  0.0f,  1.5f, 0.4f),
    UPPERCUT(false,        -0.5f,  1.0f, 0.4f),
    PUNCH(true,             0.0f,  0.6f, 0.2f),
    SLASH_SWITCH(true,     -1.5f,  1.5f, 0.7f);

    final boolean horizontal;
    final float startAngle;
    final float endAngle;
    final float radius;

    SwingArc(boolean horizontal, float startAngle, float endAngle, float radius) {
        this.horizontal = horizontal;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.radius = radius;
    }

    public static SwingArc fromPreset(String presetId) {
        if (presetId == null) return SLASH_HORIZONTAL;
        if (presetId.contains("stab")) return STAB;
        if (presetId.contains("slam")) return SLAM;
        if (presetId.contains("spin")) return SPIN;
        if (presetId.contains("swipe")) return SWIPE_HORIZONTAL;
        if (presetId.contains("uppercut")) return UPPERCUT;
        if (presetId.contains("punch")) return PUNCH;
        if (presetId.contains("switch")) return SLASH_SWITCH;
        if (presetId.contains("vertical")) return SLASH_VERTICAL;
        return SLASH_HORIZONTAL;
    }

    public float getAngle(float progress) {
        return Mth.lerp(progress, startAngle, endAngle);
    }
}
