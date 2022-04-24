package com.burchard36.api.inventory.interfaces;

import com.burchard36.api.inventory.actions.ClickableItemAction;

@FunctionalInterface
public interface GuiClickableItem {
    void onItemClick(ClickableItemAction action);
}
