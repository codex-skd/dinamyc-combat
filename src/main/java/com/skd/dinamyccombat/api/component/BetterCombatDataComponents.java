package com.skd.dinamyccombat.api.component;

import com.skd.dinamyccombat.DinamyCombat;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class BetterCombatDataComponents {

    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, DinamyCombat.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Identifier>> WEAPON_PRESET_ID =
            DATA_COMPONENTS.register("weapon_preset_id", () ->
                    DataComponentType.<Identifier>builder()
                            .persistent(Identifier.CODEC)
                            .networkSynchronized(Identifier.STREAM_CODEC)
                            .cacheEncoding()
                            .build());

    public static void init(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

    public static void bootstrap() {
    }
}
