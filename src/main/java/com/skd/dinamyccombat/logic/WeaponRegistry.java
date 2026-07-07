package com.skd.dinamyccombat.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;
import com.skd.dinamyccombat.DinamyCombat;
import com.skd.dinamyccombat.api.AttributesContainer;
import com.skd.dinamyccombat.api.WeaponAttributes;
import com.skd.dinamyccombat.api.WeaponAttributesHelper;
import com.skd.dinamyccombat.api.component.BetterCombatDataComponents;
import com.skd.dinamyccombat.network.Packets;
import com.skd.dinamyccombat.utils.CompressionHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponRegistry {
    static final Logger LOGGER = LogUtils.getLogger();
    static Map<Identifier, WeaponAttributes> registrations = new HashMap<>();
    static Map<Identifier, AttributesContainer> containers = new HashMap<>();

    public static void register(Identifier itemId, WeaponAttributes attributes) {
        registrations.put(itemId, attributes);
    }

    static WeaponAttributes getAttributes(Identifier itemId) {
        return registrations.get(itemId);
    }

    public static WeaponAttributes getAttributes(ItemStack itemStack) {
        if (itemStack == null) return null;
        var component = itemStack.get(BetterCombatDataComponents.WEAPON_PRESET_ID);
        if (component != null) {
            var container = containers.get(component);
            if (container != null) return container.attributes();
        }
        Item item = itemStack.getItem();
        Identifier id = BuiltInRegistries.ITEM.getKey(item);
        return WeaponRegistry.getAttributes(id);
    }

    public static void loadAttributes(ResourceManager resourceManager) {
        loadContainers(resourceManager);
        containers.forEach((itemId, container) -> {
            if (!BuiltInRegistries.ITEM.containsKey(itemId)) return;
            resolveAndRegisterAttributes(itemId, container);
        });
    }

    private static void loadContainers(ResourceManager resourceManager) {
        Map<Identifier, AttributesContainer> loaded = new HashMap<>();
        for (var entry : resourceManager.listResources("weapon_attributes",
                fileName -> fileName.getPath().endsWith(".json")).entrySet()) {
            var identifier = entry.getKey();
            var resource = entry.getValue();
            try {
                String json = new String(resource.open().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                AttributesContainer container = WeaponAttributesHelper.gson().fromJson(json, AttributesContainer.class);
                var id = identifier.toString().replace("weapon_attributes/", "");
                id = id.substring(0, id.lastIndexOf('.'));
                loaded.put(Identifier.parse(id), container);
            } catch (Exception e) {
                System.err.println("Failed to parse: " + identifier);
                e.printStackTrace();
            }
        }
        containers = loaded;
        Map<Identifier, AttributesContainer> resolved = new HashMap<>();
        for (var entry : containers.entrySet()) {
            var id = entry.getKey();
            var container = entry.getValue();
            if (container.parent() != null) {
                var resolvedAttrs = resolveAttributes(id, container);
                if (resolvedAttrs != null) container = new AttributesContainer(null, resolvedAttrs);
            }
            resolved.put(id, container);
        }
        containers = resolved;
    }

    public static WeaponAttributes resolveAttributes(Identifier itemId, AttributesContainer container) {
        try {
            ArrayList<WeaponAttributes> chain = new ArrayList<>();
            AttributesContainer current = container;
            while (current != null) {
                chain.add(0, current.attributes());
                if (current.parent() != null)
                    current = containers.get(Identifier.parse(current.parent()));
                else
                    current = null;
            }
            var empty = WeaponAttributes.empty();
            var resolved = chain.stream().reduce(empty, (a, b) -> {
                if (b == null) return a;
                return mergeAttributes(a, b);
            });
            WeaponAttributesHelper.validate(resolved);
            return resolved;
        } catch (Exception e) {
            LOGGER.error("Failed to resolve weapon attributes for: " + itemId, e);
            return null;
        }
    }

    public static void resolveAndRegisterAttributes(Identifier itemId, AttributesContainer container) {
        var resolved = resolveAttributes(itemId, container);
        if (resolved != null) register(itemId, resolved);
    }

    private static WeaponAttributes mergeAttributes(WeaponAttributes base, WeaponAttributes override) {
        if (override == null) return base;
        double attackRange = override.attackRange() != 0 ? override.attackRange() : base.attackRange();
        double rangeBonus = override.rangeBonus() != 0 ? override.rangeBonus() : base.rangeBonus();
        String pose = override.pose() != null ? override.pose() : base.pose();
        String offHandPose = override.offHandPose() != null ? override.offHandPose() : base.offHandPose();
        Boolean twoHanded = override.two_handed() != null ? override.two_handed() : base.two_handed();
        String category = override.category() != null ? override.category() : base.category();
        WeaponAttributes.Attack[] attacks = override.attacks() != null ? override.attacks() : base.attacks();
        var trailAppearance = override.trailAppearance() != null ? override.trailAppearance() : base.trailAppearance();
        return new WeaponAttributes(attackRange, rangeBonus, pose, offHandPose,
                twoHanded, category, attacks, trailAppearance);
    }

    private static Encoded encodedRegistrations = new Encoded(true, List.of());
    public record Encoded(boolean compressed, List<String> chunks) {}
    private static final int CHUNK_SIZE = 10000;
    private static final Gson gson = new GsonBuilder().create();

    public static class SyncFormat {
        public Map<String, AttributesContainer> attributes = new HashMap<>();
        public Map<String, WeaponAttributes> registrations = new HashMap<>();
    }

    public static void encodeRegistry() {
        List<String> chunks = new ArrayList<>();
        var syncContent = new SyncFormat();
        containers.forEach((key, value) -> syncContent.attributes.put(key.toString(), value));
        registrations.forEach((key, value) -> syncContent.registrations.put(key.toString(), value));
        var json = gson.toJson(syncContent);
        json = CompressionHelper.gzipCompress(json);
        for (int i = 0; i < json.length(); i += CHUNK_SIZE)
            chunks.add(json.substring(i, Math.min(json.length(), i + CHUNK_SIZE)));
        encodedRegistrations = new Encoded(true, chunks);
        LOGGER.info("Encoded Weapon Attribute registry: " + chunks.size() + " chunks");
    }

    public static void decodeRegistry(Packets.WeaponRegistrySync syncPacket) {
        String json = String.join("", syncPacket.chunks());
        if (syncPacket.compressed()) json = CompressionHelper.gzipDecompress(json);
        LOGGER.info("Decoded Weapon Attribute registry in " + syncPacket.chunks().size() + " chunks");
        SyncFormat sync = gson.fromJson(json, SyncFormat.class);
        containers.clear();
        sync.attributes.forEach((key, value) -> containers.put(Identifier.parse(key), value));
        registrations.clear();
        sync.registrations.forEach((key, value) -> registrations.put(Identifier.parse(key), value));
    }

    public static Encoded getEncodedRegistry() { return encodedRegistrations; }
}
