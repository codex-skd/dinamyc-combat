package com.skd.dinamyccombat.config;

import com.skd.dinamyccombat.api.fx.ConditionalTrailAppearance;
import com.skd.dinamyccombat.api.fx.ParticlePlacement;
import com.skd.dinamyccombat.api.fx.TrailAppearance;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class TrailConfig {
    public ConditionalTrailAppearance trail_appearance;
    public LinkedHashMap<String, List<ParticlePlacement>> animation_based;

    public TrailConfig() {
        this.trail_appearance = new ConditionalTrailAppearance(Identifier.fromNamespaceAndPath("dinamyc_combat", "default"), TrailAppearance.EMPTY);
        this.animation_based = new LinkedHashMap<>();
    }

    public TrailConfig(ConditionalTrailAppearance trail_appearance,
                       LinkedHashMap<String, List<ParticlePlacement>> animation_based) {
        this.trail_appearance = trail_appearance;
        this.animation_based = animation_based;
    }
}
