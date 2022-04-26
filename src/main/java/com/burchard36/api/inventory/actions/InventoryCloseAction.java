package com.burchard36.api.inventory.actions;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Executor method for {@link com.burchard36.api.inventory.interfaces.GuiCloseAction}
 */
public record InventoryCloseAction(InventoryCloseEvent closeEvent) {

}
