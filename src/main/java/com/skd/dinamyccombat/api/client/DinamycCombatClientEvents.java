package com.skd.dinamyccombat.api.client;

import com.skd.dinamyccombat.api.event.Publisher;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DinamycCombatClientEvents {

    public static final Publisher<AttackStart> ATTACK_START = new Publisher<>();
    public static final Publisher<AttackHit> ATTACK_HIT = new Publisher<>();

    public record AttackStart(Player player, InteractionHand hand) {}

    public record AttackHit(Player player, LivingEntity target, InteractionHand hand) {}
}
