package com.burchard36.inventory;

import com.burchard36.ApiLib;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.burchard36.ApiLib.convert;

public class ItemWrapper {

    private final ItemStack itemStack;
    private ItemMeta itemMeta;
    private String displayName = null;
    private List<String> lore = null;
    private final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().build();

    public ItemWrapper(final ItemStack stack) {
        if (stack == null) throw new IllegalArgumentException("ItemStack in ItemWrapper cannot be null!");
        this.itemStack = stack;
        if (stack.getType() != Material.AIR) this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Might be null
     * @return String of display name, or null
     */
    public final String getDisplayName() {
        return this.displayName;
    }

    /**
     * Might be null
     * @return List of Strings for the lore, or null
     */
    public final List<String> getLore() {
        return this.lore;
    }

    public final ItemWrapper setDisplayName(final String displayName) {
        if (this.itemMeta == null) return this;
        this.displayName = displayName;
        this.itemMeta.displayName(serializer.deserialize(convert(this.displayName)));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper setItemLore(final List<String> itemLore) {
        if (this.itemMeta == null) return this;
        this.lore = itemLore;
        final List<Component> colored = new ArrayList<>();
        itemLore.forEach((loreItem) -> {
            colored.add(serializer.deserialize(convert(loreItem)));
        });
        this.itemMeta.lore(colored);

        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper setModelData(final int data) {
        if (this.itemMeta == null) return this;
        this.itemMeta.setCustomModelData(data);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper addEnchantment(final Enchantment enchantment, final int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public final ItemWrapper addEnchantments(final HashMap<Enchantment, Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    public final ItemWrapper setSkullTo(final UUID playerUuid) {
        if (this.itemStack.getType() != Material.PLAYER_HEAD) return this;

        final SkullMeta meta = (SkullMeta) this.itemMeta;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerUuid));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public final ItemWrapper addDataString(final String key, final String value) {
        if (this.itemMeta == null) return this;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        if (this.hasDataString(key)) return this;
        dataContainer.set(nameKey, PersistentDataType.STRING, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper addDataInteger(final String key, final int value) {
        if (this.itemMeta == null) return this;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        if (this.hasDataInteger(key)) return this;
        dataContainer.set(nameKey, PersistentDataType.INTEGER, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final String getStringDataValue(final String key) {
        if (this.itemMeta == null) return null;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.STRING);
    }

    public final Integer getIntegerDataValue(final String key) {
        if (this.itemMeta == null) return null;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.INTEGER);
    }

    public final boolean hasDataString(final String key) {
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return this.itemMeta.getPersistentDataContainer().has(nameKey, PersistentDataType.STRING);
    }

    public final boolean hasDataInteger(final String key) {
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return this.itemMeta.getPersistentDataContainer().has(nameKey, PersistentDataType.INTEGER);
    }

    /**
     * Returns the ItemStack from constructor
     * @return ItemStack, may be modified!
     */
    public final ItemStack getItemStack() {
        return this.itemStack;
    }
}
