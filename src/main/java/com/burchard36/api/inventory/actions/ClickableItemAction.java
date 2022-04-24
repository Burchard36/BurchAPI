package com.burchard36.api.inventory.actions;

import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickableItemAction extends InventoryClickEvent {
    public ClickableItemAction(final InventoryClickEvent event) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
    }
}
