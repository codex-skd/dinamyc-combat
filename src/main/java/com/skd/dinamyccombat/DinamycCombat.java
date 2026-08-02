package com.skd.dinamyccombat;

import com.mojang.logging.LogUtils;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import com.skd.dinamyccombat.logic.WeaponAttributesFallback;
import com.skd.dinamyccombat.compat.CompatFeatures;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public class DinamycCombat {
    public static final String MODID = "dinamyc_combat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Dinamyc Combat initializing...");
        CompatFeatures.init();
    }

    public static void loadWeaponAttributes(MinecraftServer server) {
        WeaponRegistry.loadAttributes(server.getResourceManager());
        WeaponAttributesFallback.initialize();
        WeaponRegistry.encodeRegistry();
    }
}
