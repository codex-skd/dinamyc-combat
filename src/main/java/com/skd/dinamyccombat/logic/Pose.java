package com.skd.dinamyccombat.logic;

public record Pose(String base, String offHand) {
    public static final Pose NONE = new Pose("", "");
}
