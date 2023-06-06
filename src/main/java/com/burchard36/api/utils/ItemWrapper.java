package com.burchard36.api.utils;

import com.burchard36.api.BurchAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.burchard36.api.BurchAPI.convert;

/**
 * A basic class to wrap around {@link ItemStack}
 */
public class ItemWrapper {

    protected final ItemStack itemStack;
    protected ItemMeta itemMeta;
    protected String displayName = null;
    protected List<String> lore = new ArrayList<>();
    protected @Nullable Player owningPlayer = null;

    /**
     * Specifies directly what {@link ItemStack} you want to wrap around
     * @param stack A {@link ItemStack}
     */
    public ItemWrapper(@Nonnull final ItemStack stack) {
        this.itemStack = stack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Creates a {@link ItemStack} with a {@link Integer} of 1 as the amount
     * @param material A {@link Material} enum
     */
    public ItemWrapper(@Nonnull final Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Creates a {@link ItemStack} with a specific {@link Material} and {@link Integer} amount
     * @param material A {@link Material} to use
     * @param amount A {@link Integer} amount
     */
    public ItemWrapper(@Nonnull final Material material, final int amount) {
        this.itemStack = new ItemStack(material, amount);
        if (this.itemStack.getType() != Material.AIR) this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Specifies directly what {@link ItemStack} you want to wrap around
     * <br>
     * The {@link Player} argument is for if you want certain support for things like PlaceholderAPI to parse
     * placeholders on this item for a specific player
     * @param stack An {@link ItemStack} cant be null
     * @param player A {@link Player} is nullable
     */
    public ItemWrapper(final @Nonnull ItemStack stack, @Nullable Player player) {
        this.itemStack = stack;
        this.itemMeta = this.itemStack.getItemMeta();
        this.owningPlayer = player;
    }

    /**
     * Creates a {@link ItemStack} with a {@link Integer} of 1 as the amount
     * <br>
     * This also allows you to put a {@link Player} as the second argument, to allow PlaceholderAPI parsing.
     * @param material A {@link Material} enum
     * @param player A nullable {@link Player}
     */
    public ItemWrapper(@Nonnull final Material material, @Nullable final Player player) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = this.itemStack.getItemMeta();
        this.owningPlayer = player;
    }

    /**
     * Creates a {@link ItemStack} with a specific {@link Material} and {@link Integer} amount
     * <br>
     * This also allows you to specify a {@link Player} for PlaceholderAPI parsing.
     * @param material A {@link Material} enum
     * @param amount A {@link Integer} amount, value cant be <= 0
     * @param player A nullable {@link Player}
     */
    public ItemWrapper (@Nonnull final Material material,
                        final int amount,
                        @Nullable final Player player) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
        this.owningPlayer = player;
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

    /**
     * Sets the display name of the wrapped {@link ItemStack}
     * @param displayName A {@link String} to set the {@link ItemStack}'s name to, color codes automatically handled
     * @return Instance of this {@link ItemWrapper}
     */
    public final ItemWrapper setDisplayName(final String displayName) {
        if (this.itemMeta == null) return this;
        this.displayName = displayName;
        this.itemMeta.setDisplayName(convert(this.displayName, null));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * Adds a Lore line to the linked {@link ItemStack}
     * @param lore a list of {@link String}'s, color codes automatically handled
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper addItemLore(final String... lore) {
        for (int i = 0; i <= lore.length; i++) {
            this.lore.add(convert(lore[i], null));
        }
        this.setItemLore(this.lore);
        return this;
    }

    /**
     * Sets the items Lore directly
     * @param itemLore A {@link List} of {@link String}'s
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper setItemLore(final List<String> itemLore) {
        if (this.itemMeta == null) return this;
        this.lore = itemLore;
        final List<String> colored = new ArrayList<>();
        itemLore.forEach((loreItem) -> {
            colored.add(convert(loreItem, null));
        });
        this.itemMeta.setLore(colored);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * Sets the CustomModelData {@link Integer} of the {@link ItemStack}
     * @param data A {@link Integer} to set
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper setModelData(final int data) {
        if (this.itemMeta == null) return this;
        this.itemMeta.setCustomModelData(data);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * Adds a specific {@link Enchantment} with a specified {@link Integer} level
     * @param enchantment An {@link Enchantment} to apply
     * @param level A {@link Integer} level to set
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper addEnchantment(final Enchantment enchantment, final int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds a {@link java.util.Map} of {@link Enchantment} and {@link Integer} to the linked {@link ItemStack}'s Enchantment list
     * @param enchantments a {@link java.util.Map} or {@link Enchantment} enums and {@link Integer} as level-values
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper addEnchantments(final HashMap<Enchantment, Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * If the linked {@link ItemStack} is a PLAYER_HEAD, it will set a owning player's {@link UUID}
     * @param playerUuid The player's {@link UUID}
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper setSkullTo(final UUID playerUuid) {
        if (this.itemStack.getType() != Material.PLAYER_HEAD) return this;

        final SkullMeta meta = (SkullMeta) this.itemMeta;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerUuid));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Adds a {@link String} value to the linked {@link ItemStack}'s {@link PersistentDataContainer}
     * @param key A {@link String} value for the key to be set as
     * @param value A {@link String} value for the value to be set as
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper addDataString(final String key, final String value) {
        if (this.itemMeta == null) return this;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        if (this.hasDataString(key)) return this;
        dataContainer.set(nameKey, PersistentDataType.STRING, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * Adds a {@link Integer} value to the linked {@link ItemStack}'s {@link PersistentDataContainer}
     * @param key A {@link String} value for the key to be set as
     * @param value A {@link Integer} value for the value to be set as
     * @return instance of this {@link ItemWrapper}
     */
    public final ItemWrapper addDataInteger(final String key, final int value) {
        if (this.itemMeta == null) return this;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        if (this.hasDataInteger(key)) return this;
        dataContainer.set(nameKey, PersistentDataType.INTEGER, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * Gets a {@link String} value form the linked {@link ItemStack}'s {@link PersistentDataContainer}
     * @param key A {@link String} key to search under
     * @return A {@link String} if a value was found, null if not
     */
    public final String getStringDataValue(final String key) {
        if (this.itemMeta == null) return null;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.STRING);
    }

    /**
     * Gets a {@link Integer} value form the linked {@link ItemStack}'s {@link PersistentDataContainer}
     * @param key A {@link String} key to search under
     * @return A {@link Integer} if a value was found, null if not
     */
    public final Integer getIntegerDataValue(final String key) {
        if (this.itemMeta == null) return null;
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.INTEGER);
    }

    /**
     * Checks if the linked {@link ItemStack}'s {@link PersistentDataContainer} has a {@link String} value
     * @param key A {@link String} key to search under
     * @return A {@link Boolean}, true if value was found
     */
    public final boolean hasDataString(final String key) {
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        return this.itemMeta.getPersistentDataContainer().has(nameKey, PersistentDataType.STRING);
    }

    /**
     * Checks if the linked {@link ItemStack}'s {@link PersistentDataContainer} has a {@link Integer} value
     * @param key A {@link String} key to search under
     * @return A {@link Boolean}, true if value was found
     */
    public final boolean hasDataInteger(final String key) {
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
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
