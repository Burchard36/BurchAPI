package com.burchard36.inventory.interfaces;

import com.burchard36.inventory.actions.InventoryOpenAction;

@FunctionalInterface
public interface GuiOpenAction {
    void onOpen(InventoryOpenAction action);
}
