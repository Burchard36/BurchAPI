package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryOpenAction;

@FunctionalInterface
public interface GuiOpenAction {
    void onOpen(InventoryOpenAction action);
}
