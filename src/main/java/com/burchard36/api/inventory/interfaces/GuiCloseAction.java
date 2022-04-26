package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryCloseAction;

/**
 * The functional interface called when a {@link com.burchard36.api.inventory.PluginInventory} gets closed
 */
@FunctionalInterface
public interface GuiCloseAction {
    /**
     * The method called when the {@link com.burchard36.api.inventory.PluginInventory} gets closed
     * @param action A {@link InventoryCloseAction}
     */
    void onClose(InventoryCloseAction action);
}
