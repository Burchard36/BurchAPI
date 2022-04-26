package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryClickAction;

/**
 * The functional interface called when a {@link com.burchard36.api.inventory.PluginInventory}'s inventory is clicked on
 *
 * This is NOT to handle clicks of items in a inventory, see {@link com.burchard36.api.inventory.ClickableItem} instead for this
 *
 */
@FunctionalInterface
public interface GuiClickAction {
    /**
     * The method called when a {@link com.burchard36.api.inventory.PluginInventory} gets clicked
     * @param action A {@link InventoryClickAction}
     */
    void onClick(final InventoryClickAction action);
}
