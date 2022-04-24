package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryClickAction;

@FunctionalInterface
public interface GuiClickAction {
    void onClick(final InventoryClickAction action);
}
