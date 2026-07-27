package com.skd.dinamyccombat.mixin.player;

import com.skd.dinamyccombat.config.ServerConfig;
import com.skd.dinamyccombat.logic.PlayerAttackProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin implements PlayerAttackProperties {

    @Unique
    private int dinamyc_combat$comboCount = 0;

    @Unique
    private int dinamyc_combat$lastAttackTime = 0;

    @Unique
    private int dinamyc_combat$comboTimeout = 0;

    @Inject(method = "attack", at = @At("HEAD"))
    private void dinamyc_combat$onAttackStart(Entity target, CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (!self.level().isClientSide()) {
            int currentTime = self.tickCount;
            if (currentTime - dinamyc_combat$lastAttackTime < 40) {
                dinamyc_combat$comboCount++;
            } else {
                dinamyc_combat$comboCount = 1;
            }
            dinamyc_combat$lastAttackTime = currentTime;
            dinamyc_combat$comboTimeout = 40;
        }
    }

    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 0)
    private float dinamyc_combat$modifyAttackDamage(float damage) {
        Player self = (Player) (Object) this;
        float cooldown = self.getAttackStrengthScale(0.5F);

        return damage;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void dinamyc_combat$onTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (dinamyc_combat$comboTimeout > 0) {
            dinamyc_combat$comboTimeout--;
            if (dinamyc_combat$comboTimeout == 0) {
                dinamyc_combat$comboCount = 0;
            }
        }
    }

    @Override
    public int getComboCount() {
        return dinamyc_combat$comboCount;
    }

    @Override
    public void setComboCount(int comboCount) {
        this.dinamyc_combat$comboCount = comboCount;
    }

    @Unique
    public boolean dinamyc_combat$isTwoHandedWielding() {
        Player self = (Player) (Object) this;
        ItemStack mainHand = self.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = self.getItemInHand(InteractionHand.OFF_HAND);
        return !mainHand.isEmpty() && !offHand.isEmpty();
    }
}
