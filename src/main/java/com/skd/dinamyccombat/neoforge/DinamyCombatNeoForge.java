package com.skd.dinamyccombat.neoforge;

import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.config.ClientConfig;
import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.neoforge.attachment.DinamyCombatPlayerAttachments;
import com.skd.dinamyccombat.utils.SoundHelper;
import com.skd.dinamyccombat.particle.BetterCombatParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(DinamyCombat.MODID)
public final class DinamyCombatNeoForge {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DinamyCombat.MODID);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, DinamyCombat.MODID);

    static {
        SoundHelper.soundKeys.forEach(key ->
                SOUND_EVENTS.register(key, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(DinamyCombat.MODID, key))));
    }

    public DinamyCombatNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        DinamyCombat.init();
        DinamyCombatPlayerAttachments.init(ATTACHMENT_TYPES);
        modEventBus.addListener(RegisterEvent.class, event -> {
            if (event.getRegistryKey() == Registries.PARTICLE_TYPE) {
                BetterCombatParticles.register();
            }
        });
        SOUND_EVENTS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
