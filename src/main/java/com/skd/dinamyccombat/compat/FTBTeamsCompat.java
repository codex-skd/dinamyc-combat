package com.skd.dinamyccombat.compat;

import com.skd.dinamyccombat.logic.TargetHelper;
import net.neoforged.fml.ModList;
import net.minecraft.world.entity.player.Player;

public class FTBTeamsCompat {
    public static void init() {
        if (ModList.get().isLoaded("ftbteams")) {
            TargetHelper.registerTeamMatcher("ftb", (attacker, target) -> {
                if (attacker instanceof Player && target instanceof Player) {
                    return null;
                }
                return null;
            });
        }
    }
}
