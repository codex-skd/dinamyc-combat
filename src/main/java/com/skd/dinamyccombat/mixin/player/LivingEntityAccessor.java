package com.skd.dinamyccombat.mixin.player;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("attackStrengthTicker")
    int getAttackStrengthTicker();

    @Accessor("attackStrengthTicker")
    void setAttackStrengthTicker(int value);

    @Accessor("invulnerableTime")
    int getInvulnerableTime();

    @Accessor("invulnerableTime")
    void setInvulnerableTime(int value);

    @Accessor("lastHurtByPlayerTime")
    int getLastHurtByPlayerTime();

    @Accessor("lastHurtByPlayerTime")
    void setLastHurtByPlayerTime(int value);
}
