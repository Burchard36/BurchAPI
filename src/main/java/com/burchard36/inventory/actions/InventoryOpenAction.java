package com.burchard36.inventory.actions;

import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenAction extends InventoryOpenEvent {
    public InventoryOpenAction(final InventoryOpenEvent event) {
        super(event.getView());
    }
}
