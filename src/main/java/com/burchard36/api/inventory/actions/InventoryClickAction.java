package com.burchard36.api.inventory.actions;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Executor method for {@link com.burchard36.api.inventory.interfaces.GuiClickAction}
 */
public record InventoryClickAction(InventoryClickEvent clickEvent) {

}
