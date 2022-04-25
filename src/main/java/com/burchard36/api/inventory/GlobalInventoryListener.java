package com.burchard36.api.inventory;

import com.burchard36.api.inventory.actions.ClickableItemAction;
import com.burchard36.api.inventory.actions.InventoryClickAction;
import com.burchard36.api.inventory.actions.InventoryCloseAction;
import com.burchard36.api.inventory.actions.InventoryOpenAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GlobalInventoryListener implements Listener {

    private final List<PluginInventory> inventoriesQueued;

    public GlobalInventoryListener(JavaPlugin pluginInstance) {
        this.inventoriesQueued = new ArrayList<>();
        pluginInstance.getServer().getPluginManager().registerEvents(this, pluginInstance);
    }

    public void queuePluginInventory(PluginInventory inventory) {
        this.inventoriesQueued.add(inventory);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onOpen(InventoryCloseEvent event) {
        final List<PluginInventory> toRemove = new ArrayList<>();

        for (Iterator<PluginInventory> it = toRemove.iterator(); it.hasNext();) {
            PluginInventory inv = it.next();


        }

        for (final PluginInventory inventory : this.inventoriesQueued) {
            if (!this.isInventoriesTurn(event.getInventory(), inventory)) continue;

            if (inventory.closeAction != null) {
                toRemove.add(inventory);
                inventory.closeAction.onClose(new InventoryCloseAction(event));
            }
        }

        this.inventoriesQueued.removeAll(toRemove); // When inventories close, we need to not listen to them anymore
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onOpen(InventoryOpenEvent event) {
        for (final PluginInventory inventory : this.inventoriesQueued) {
            if (!this.isInventoriesTurn(event.getInventory(), inventory)) continue;

            if (inventory.openAction != null) {
                inventory.openAction.onOpen(new InventoryOpenAction(event));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        for (final PluginInventory inventory : this.inventoriesQueued) {
            if (!this.isInventoriesTurn(event.getInventory(), inventory)) continue;

            if (inventory.clickAction != null) {
                int slotClicked = event.getSlot();
                final ClickableItem item = inventory.clickableItems.get(slotClicked);
                if (item != null && item.guiClickableItem != null) {
                    item.guiClickableItem.onItemClick(new ClickableItemAction(event));
                }

                inventory.clickAction.onClick(new InventoryClickAction(event));
            }
        }
    }

    private boolean isInventoriesTurn(final Inventory clickedInventory, final PluginInventory checkingInventory) {
        final InventoryHolder clickedHolder = clickedInventory.getHolder();

        if (clickedHolder instanceof PluginInventory.PluginHolder pluginHolder) {
            final PluginInventory.PluginHolder inventoryHolder =
                    (PluginInventory.PluginHolder) checkingInventory.inventoryHolder;
            if (!pluginHolder.uuid.equals(inventoryHolder.uuid)) return false;
        } else return false;
        return true;
    }
}
