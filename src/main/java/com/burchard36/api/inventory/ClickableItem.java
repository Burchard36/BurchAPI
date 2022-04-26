package com.burchard36.api.inventory;

import com.burchard36.api.inventory.interfaces.GuiClickableItem;
import com.burchard36.api.utils.ItemWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A clickable item the executes methods on a {@link PluginInventory}'s {@link org.bukkit.event.inventory.InventoryClickEvent}
 * if, you have the functional interface method set of course
 */
public class ClickableItem extends ItemWrapper {

    private final Material itemMaterial;
    private final int itemAmount;
    private final ItemWrapper wrapper;

    /**
     * The {@link GuiClickableItem} action for this class
     */
    public GuiClickableItem guiClickableItem;

    /**
     * Creates a instnace of this class with a {@link Material} and a {@link Integer} amount
     * @param type A {@link Material} to set for this item
     * @param amount A {@link Integer} amount to set for this item
     */
    public ClickableItem(final Material type, final int amount) {
        super(type, amount);
        this.itemMaterial = type;
        this.itemAmount = amount;
        this.wrapper = new ItemWrapper(new ItemStack(this.itemMaterial, this.itemAmount));
    }

    /**
     * Supports creating a lore with a material, amount, item name, and item lore
     * @param type A {@link Material}
     * @param amount A {@link Integer} amount for this {@link ItemStack} to have
     * @param itemName A {@link String} name for this item to have, supports ampersand symbols for coloring TODO: Make this Nullable
     * @param itemLore A {@link List} of {@link String} for the lore to have, supports ampersand symbols for coloring TODO: Make this Nullable
     */
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

    /**
     * Sets the onClick action of this {@link ClickableItem}
     * @param clickableItemAction The {@link GuiClickableItem} action to set for this functional interface (Its a lambda function)
     * @return instance of this {@link ClickableItem}
     */
    public ClickableItem onClick(final GuiClickableItem clickableItemAction) {
        this.guiClickableItem = clickableItemAction;
        return this;
    }

    /**
     * returns the {@link ItemStack} that this class currently holds
     * @return A {@link ItemStack}
     */
    public ItemStack build() {
        return this.wrapper.getItemStack();
    }
}
