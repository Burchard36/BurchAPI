package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryOpenAction;

/**
 * The functional interface for when {@link com.burchard36.api.inventory.PluginInventory}'s get opened
 */
@FunctionalInterface
public interface GuiOpenAction {
    /**
     * The method called when {@link com.burchard36.api.command.ApiCommand} gets opened
     * @param action A {@link InventoryOpenAction}
     */
    void onOpen(InventoryOpenAction action);
}
