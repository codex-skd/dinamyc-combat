package com.skd.dinamyccombat.particle;

import com.skd.dinamyccombat.DinamycCombat;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.core.particles.ParticleTypes;

import java.util.ArrayList;
import java.util.List;

public class DinamycParticles {
    public record Entry(ParticleType<SlashParticleEffect> particleType, StaticParams params) {}
    public record StaticParams(int lifetime, float scale) {
        public static final StaticParams DEFAULT = new StaticParams(6, 1.5F);
    }

    public static final List<Entry> ENTRIES = new ArrayList<>();

    public static ParticleType<SlashParticleEffect> topslash180;
    public static ParticleType<SlashParticleEffect> topslash270;
    public static ParticleType<SlashParticleEffect> topslash360;
    public static ParticleType<SlashParticleEffect> topslash45;
    public static ParticleType<SlashParticleEffect> topslash90;
    public static ParticleType<SlashParticleEffect> topstab;
    public static ParticleType<SlashParticleEffect> botslash180;
    public static ParticleType<SlashParticleEffect> botslash270;
    public static ParticleType<SlashParticleEffect> botslash360;
    public static ParticleType<SlashParticleEffect> botslash45;
    public static ParticleType<SlashParticleEffect> botslash90;
    public static ParticleType<SlashParticleEffect> botstab;

    public static void register() {
        topslash180 = register("topslash180", StaticParams.DEFAULT);
        topslash270 = register("topslash270", StaticParams.DEFAULT);
        topslash360 = register("topslash360", StaticParams.DEFAULT);
        topslash45 = register("topslash45", StaticParams.DEFAULT);
        topslash90 = register("topslash90", StaticParams.DEFAULT);
        topstab = register("topstab", StaticParams.DEFAULT);
        botslash180 = register("botslash180", StaticParams.DEFAULT);
        botslash270 = register("botslash270", StaticParams.DEFAULT);
        botslash360 = register("botslash360", StaticParams.DEFAULT);
        botslash45 = register("botslash45", StaticParams.DEFAULT);
        botslash90 = register("botslash90", StaticParams.DEFAULT);
        botstab = register("botstab", StaticParams.DEFAULT);
    }

    private static ParticleType<SlashParticleEffect> register(String name, StaticParams params) {
        var type = new ParticleType<SlashParticleEffect>(false) {
            @Override
            public MapCodec<SlashParticleEffect> codec() {
                return SlashParticleEffect.CODEC;
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, SlashParticleEffect> streamCodec() {
                return SlashParticleEffect.STREAM_CODEC;
            }
        };
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(DinamycCombat.MODID, name), type);
        ENTRIES.add(new Entry(type, params));
        return type;
    }
}
