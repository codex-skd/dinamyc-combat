package com.skd.dinamyccombat.neoforge;

import com.skd.dinamyccombat.DinamycCombat;
import com.skd.dinamyccombat.neoforge.attachment.DinamycCombatPlayerAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

public class PlatformHelper {
    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static FriendlyByteBuf createByteBuffer() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    public static Collection<ServerPlayer> tracking(ServerPlayer player) {
        return ((ServerLevel) player.level()).players();
    }

    public static Collection<ServerPlayer> around(ServerLevel world, net.minecraft.world.phys.Vec3 origin, double distance) {
        return world.players();
    }

    public static boolean networkS2C_CanSend(ServerPlayer player, Identifier packetId) {
        return true;
    }

    public static void networkS2C_Send(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void networkC2S_Send(CustomPacketPayload payload) {
        var connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.send(payload);
        }
    }

    public static String getMainHandIdleAnimation(Player player) {
        return DinamycCombatPlayerAttachments.getMainHandIdleAnimation(player);
    }

    public static String getOffHandIdleAnimation(Player player) {
        return DinamycCombatPlayerAttachments.getOffHandIdleAnimation(player);
    }

    public static void setMainHandIdleAnimation(Player player, String animation) {
        DinamycCombatPlayerAttachments.setMainHandIdleAnimation(player, animation);
    }

    public static void setOffHandIdleAnimation(Player player, String animation) {
        DinamycCombatPlayerAttachments.setOffHandIdleAnimation(player, animation);
    }
}
