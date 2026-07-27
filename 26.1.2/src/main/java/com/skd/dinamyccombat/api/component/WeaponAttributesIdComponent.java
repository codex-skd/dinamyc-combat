package com.skd.dinamyccombat.api.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record WeaponAttributesIdComponent(Identifier id) {

    public static final Codec<WeaponAttributesIdComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(WeaponAttributesIdComponent::id)
            ).apply(instance, WeaponAttributesIdComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, WeaponAttributesIdComponent> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC,
                    WeaponAttributesIdComponent::id,
                    WeaponAttributesIdComponent::new
            );
}
