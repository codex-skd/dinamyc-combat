package com.skd.dinamyccombat.api.fx;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ItemConditions {

    private static final Map<Identifier, Predicate<ItemStack>> CONDITIONS = new HashMap<>();

    public static final Identifier DEFAULT = Identifier.fromNamespaceAndPath("dinamyc_combat", "default");
    public static final Identifier SWORD = Identifier.fromNamespaceAndPath("dinamyc_combat", "sword");
    public static final Identifier AXE = Identifier.fromNamespaceAndPath("dinamyc_combat", "axe");
    public static final Identifier PICKAXE = Identifier.fromNamespaceAndPath("dinamyc_combat", "pickaxe");
    public static final Identifier SHOVEL = Identifier.fromNamespaceAndPath("dinamyc_combat", "shovel");
    public static final Identifier HOE = Identifier.fromNamespaceAndPath("dinamyc_combat", "hoe");

    static {
        registerDefault(DEFAULT, stack -> true);
        registerDefault(SWORD, stack ->
                BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().contains("sword"));
        registerDefault(AXE, stack ->
                BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().contains("axe"));
        registerDefault(PICKAXE, stack ->
                BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().contains("pickaxe"));
        registerDefault(SHOVEL, stack ->
                BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().contains("shovel"));
        registerDefault(HOE, stack ->
                BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().contains("hoe"));
    }

    public static void registerDefault(Identifier id, Predicate<ItemStack> condition) {
        CONDITIONS.put(id, condition);
    }

    public static void register(Identifier id, Predicate<ItemStack> condition) {
        CONDITIONS.put(id, condition);
    }

    public static Predicate<ItemStack> get(Identifier id) {
        return CONDITIONS.getOrDefault(id, stack -> true);
    }

    public static boolean matches(Identifier id, ItemStack stack) {
        return get(id).test(stack);
    }

    public static Map<Identifier, Predicate<ItemStack>> getAll() {
        return new HashMap<>(CONDITIONS);
    }
}
