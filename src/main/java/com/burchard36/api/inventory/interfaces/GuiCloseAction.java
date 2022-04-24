package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.InventoryCloseAction;

@FunctionalInterface
public interface GuiCloseAction {

    void onClose(InventoryCloseAction action);
}
