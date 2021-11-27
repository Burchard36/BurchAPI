package com.burchard36.inventory;

import com.burchard36.ApiLib;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.burchard36.ApiLib.convert;

public class ItemWrapper {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemWrapper(final ItemStack stack) {
        this.itemStack = stack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public final ItemWrapper setDisplayName(final String displayName) {
        this.itemMeta.displayName(Component.text(convert(displayName)));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper setItemLore(final List<String> itemLore) {
        final List<Component> colored = new ArrayList<>();
        itemLore.forEach((loreItem) -> {
            colored.add(Component.text(convert(loreItem)));
        });
        this.itemMeta.lore(colored);
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

    public final ItemWrapper addDataString(final String key, final String value) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        dataContainer.set(nameKey, PersistentDataType.STRING, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper addDataInteger(final String key, final int value) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        dataContainer.set(nameKey, PersistentDataType.INTEGER, value);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public final ItemWrapper addDataBoolean(final String key, final boolean value) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        dataContainer.set(nameKey, new PersistentBooleanType(), value);
    }

    public final String getStringDataValue(final String key) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.STRING);
    }

    public final Integer getIntegerDataValue(final String key) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return dataContainer.get(nameKey, PersistentDataType.INTEGER);
    }

    public final Boolean getBooleanDataValue(final String key) {
        final PersistentDataContainer dataContainer = this.itemMeta.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return dataContainer.get(nameKey, new PersistentBooleanType());
    }

    /**
     * Returns the ItemStack from constructor
     * @return ItemStack, may be modified!
     */
    public final ItemStack getItemStack() {
        return this.itemStack;
    }
}
