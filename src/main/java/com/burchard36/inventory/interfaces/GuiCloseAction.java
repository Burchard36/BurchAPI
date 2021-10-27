package com.burchard36.inventory.interfaces;

import com.burchard36.inventory.actions.InventoryCloseAction;

@FunctionalInterface
public interface GuiCloseAction {

    void onClose(InventoryCloseAction action);
}
