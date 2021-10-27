package com.burchard36.inventory.interfaces;

import com.burchard36.inventory.actions.InventoryClickAction;

@FunctionalInterface
public interface GuiClickAction {
    void onClick(final InventoryClickAction action);
}
