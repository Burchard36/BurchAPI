package com.burchard36.inventory;

import com.burchard36.inventory.interfaces.GuiClickableItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClickableItem extends ItemWrapper {

    private final Material itemMaterial;
    private final int itemAmount;
    private final ItemWrapper wrapper;

    public GuiClickableItem guiClickableItem;

    public ClickableItem(final Material type, final int amount) {
        super(type, amount);
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.wrapper = new ItemWrapper(new ItemStack(this.itemMaterial, this.itemAmount));
    }

    public ClickableItem(final Material type,
                         final int amount,
                         final String itemName,
                         final List<String> itemLore) {
        super(type, amount);
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.wrapper = new ItemWrapper(new ItemStack(this.itemMaterial, this.itemAmount))
                .setDisplayName(itemName)
                .setItemLore(itemLore);
    }

    public ClickableItem onClick(final GuiClickableItem clickableItemAction) {
        this.guiClickableItem = clickableItemAction;
        return this;
    }

    public ItemStack build() {
        return this.wrapper.getItemStack();
    }
}
