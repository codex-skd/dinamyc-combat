package com.skd.dinamyccombat.mixin.player;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("attackStrengthTicker")
    int betterCombat_getTicksSinceLastAttack();

    @Accessor("attackStrengthTicker")
    void betterCombat_setTicksSinceLastAttack(int value);
}
