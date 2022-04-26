package com.burchard36.api.inventory.actions;

import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Executor method for {@link com.burchard36.api.inventory.interfaces.GuiOpenAction}
 */
public record InventoryOpenAction(InventoryOpenEvent openEvent) {
}
