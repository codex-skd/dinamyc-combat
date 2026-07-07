package com.skd.dinamyccombat.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class SlashParticleEffect implements ParticleOptions {
    public static final MapCodec<SlashParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("bottom").forGetter(e -> e.bottom),
                    Codec.FLOAT.fieldOf("scale").forGetter(e -> e.scale),
                    Codec.FLOAT.fieldOf("pitch").forGetter(e -> e.pitch),
                    Codec.FLOAT.fieldOf("yaw").forGetter(e -> e.yaw),
                    Codec.FLOAT.fieldOf("localYaw").forGetter(e -> e.localYaw),
                    Codec.FLOAT.fieldOf("roll").forGetter(e -> e.roll),
                    Codec.BOOL.fieldOf("light").forGetter(e -> e.light),
                    Codec.LONG.fieldOf("colorRGBA").forGetter(e -> e.colorRGBA)
            ).apply(instance, SlashParticleEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SlashParticleEffect> STREAM_CODEC = StreamCodec.of(
            (buf, effect) -> {
                buf.writeFloat(effect.bottom);
                buf.writeFloat(effect.scale);
                buf.writeFloat(effect.pitch);
                buf.writeFloat(effect.yaw);
                buf.writeFloat(effect.localYaw);
                buf.writeFloat(effect.roll);
                buf.writeBoolean(effect.light);
                buf.writeLong(effect.colorRGBA);
            },
            buf -> new SlashParticleEffect(
                    buf.readFloat(), buf.readFloat(), buf.readFloat(),
                    buf.readFloat(), buf.readFloat(), buf.readFloat(),
                    buf.readBoolean(), buf.readLong()
            )
    );

    private final float bottom;
    private final float scale;
    private final float pitch;
    private final float yaw;
    private final float localYaw;
    private final float roll;
    private final boolean light;
    private final long colorRGBA;

    public SlashParticleEffect(float bottom, float scale, float pitch, float yaw,
                               float localYaw, float roll, boolean light, long colorRGBA) {
        this.bottom = bottom;
        this.scale = scale;
        this.pitch = pitch;
        this.yaw = yaw;
        this.localYaw = localYaw;
        this.roll = roll;
        this.light = light;
        this.colorRGBA = colorRGBA;
    }

    public float getBottom() { return bottom; }
    public float getScale() { return scale; }
    public float getPitch() { return pitch; }
    public float getYaw() { return yaw; }
    public float getLocalYaw() { return localYaw; }
    public float getRoll() { return roll; }
    public boolean getLight() { return light; }
    public long getColorRGBA() { return colorRGBA; }

    @Override
    public ParticleType<?> getType() {
        return BetterCombatParticles.topstab;
    }
}
