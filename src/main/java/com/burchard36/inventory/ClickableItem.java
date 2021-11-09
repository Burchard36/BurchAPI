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
    private final ItemWrapper wrapper;

    public GuiClickableItem guiClickableItem;

    public ClickableItem(final Material type, final int amount) {
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.wrapper = new ItemWrapper(new ItemStack(this.itemMaterial, this.itemAmount));
    }

    public ClickableItem(final Material type,
                         final int amount,
                         final String itemName,
                         final List<String> itemLore) {
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.wrapper = new ItemWrapper(new ItemStack(this.itemMaterial, this.itemAmount))
                .setDisplayName(itemName)
                .setItemLore(itemLore);
    }

    public ClickableItem(final ItemWrapper itemWrapper) {
        this.wrapper = itemWrapper;
    }

    public ClickableItem onClick(final GuiClickableItem clickableItemAction) {
        this.guiClickableItem = clickableItemAction;
        return this;
    }

    public ItemStack build() {
        return this.wrapper.getItemStack();
    }
}
