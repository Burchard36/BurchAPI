package com.burchard36.inventory.interfaces;

import com.burchard36.inventory.actions.ClickableItemAction;

@FunctionalInterface
public interface GuiClickableItem {
    void onItemClick(ClickableItemAction action);
}
