package com.burchard36.inventory;

import com.burchard36.inventory.interfaces.GuiClickableItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ClickableItem {

    private Material itemMaterial;
    private int itemAmount;
    private String itemName;
    private List<Component> itemLore;

    public GuiClickableItem guiClickableItem;

    public ClickableItem(final Material type, final int amount) {
        this.itemMaterial = type;
        this.itemAmount = amount;
    }

    public ClickableItem(final Material type,
                         final int amount,
                         final String itemName,
                         final List<Component> itemLore) {
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.itemName = itemName;
        this.itemLore = itemLore;
    }

    /**
     * Sets the material of this item
     * @param material BukkitMaterial to set item to
     * @return instance of this class
     */
    public ClickableItem setMaterial(final Material material) {
        this.itemMaterial = material;
        return this;
    }

    /**
     * Sets the material of this item
     * @param amount Integer amount of this item to be displayed in GUI
     * @return instance of this class
     */
    public ClickableItem setAmount(final int amount) {
        this.itemAmount = amount;
        return this;
    }

    /**
     * Sets the display name of this item
     * @param name Name of this item to set it to (String)
     * @return instance of this class
     */
    public ClickableItem setDisplayName(final String name) {
        this.itemName = name;
        return this;
    }

    public ClickableItem onClick(final GuiClickableItem clickableItemAction) {
        this.guiClickableItem = clickableItemAction;
        return this;
    }

    public ItemStack build() {
        final ItemStack stack = new ItemStack(this.itemMaterial, this.itemAmount);
        final ItemMeta meta = stack.getItemMeta();

        if (this.itemName != null) meta.displayName(Component.text(this.itemName));
        if (this.itemLore != null) meta.lore(this.itemLore);
        stack.setItemMeta(meta);
        return stack;
    }
}
