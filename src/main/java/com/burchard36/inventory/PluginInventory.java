package com.burchard36.inventory;

import com.burchard36.inventory.actions.ClickableItemAction;
import com.burchard36.inventory.actions.InventoryClickAction;
import com.burchard36.inventory.actions.InventoryCloseAction;
import com.burchard36.inventory.actions.InventoryOpenAction;
import com.burchard36.inventory.interfaces.GuiClickAction;
import com.burchard36.inventory.interfaces.GuiCloseAction;
import com.burchard36.inventory.interfaces.GuiOpenAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;


public class PluginInventory implements Listener {

    private String inventoryTitle;
    private InventoryType inventoryType;
    private int inventorySlots;
    private InventoryHolder inventoryHolder;
    private Inventory inventory;

    private GuiCloseAction closeAction;
    private GuiOpenAction openAction;
    private GuiClickAction clickAction;

    private HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();

    public PluginInventory(final int slots, final String name) {
        this.inventory = Bukkit.createInventory(() -> inventory, slots, Component.text(name));
    }

    public PluginInventory() {}

    /**
     * Sets the inventory display name
     * @param displayName Display name to this inventory to
     * @return instance of this class
     */
    public PluginInventory setDisplayName(final String displayName) {
        this.inventoryTitle = displayName;
        return this;
    }

    /**
     * Sets the inventory size of this Inventory, if setInventoryHolder gets called this method has no effect
     * @param size Size of this inventory to set it to
     * @return instance of this class
     */
    public PluginInventory setInventorySize(final int size) {
        this.inventorySlots = size;
        return this;
    }

    /**
     * Sets the inventory type of this inventory, has priority over the inventory size
     * @param type InventoryType to set this Inventory to
     * @return instance of this class
     */
    public PluginInventory setInventoryType(final InventoryType type) {
        this.inventoryType = type;
        return this;
    }

    /**
     * Sets the holder for this inventory
     * @param holder InventoryHolder for this Inventory
     * @return instance of this class
     */
    public PluginInventory setInventoryHolder(final InventoryHolder holder) {
        this.inventoryHolder = holder;
        return this;
    }

    /**
     * Registers events to this class
     * @param plugin JavaPlugin to register events too
     * @return instance of this Class
     */
    public PluginInventory register(final JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return this;
    }

    /**
     * Lambda interface called when inventory gets closed
     * @param closeAction GuiCloseAction method
     * @return instance of this class
     */
    public PluginInventory onClose(final GuiCloseAction closeAction) {
        this.closeAction = closeAction;
        return this;
    }

    /**
     * Lambda interface called when inventory gets opened
     * @param openAction GuiOpenAction method
     * @return instance of this class
     */
    public PluginInventory onOpen(final GuiOpenAction openAction) {
        this.openAction = openAction;
        return this;
    }

    /**
     * Lambda interface called when inventory gets clicked
     * @param clickAction GuiClickAction method
     * @return instance of this class
     */
    public PluginInventory onClick(final GuiClickAction clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    /**
     * Adds clickable items at a slot, gets overridden by addClickableItem(ClickableItem item)
     * @param slot Slot to add item to
     * @param item ClickableItem to add
     * @return instance of this class
     */
    public PluginInventory addClickableItemAtSlot(final int slot, final ClickableItem item) {
        this.clickableItems.put(slot, item);
        return this;
    }

    public PluginInventory addClickableItems(final List<ClickableItem> items) {
        int x = 0;
        for (final ClickableItem item : items) {
            this.clickableItems.put(x, item);
            x++;
        }
        return this;
    }

    /**
     * Opens a inventory to a Player
     * @param player Player to open inventory to
     */
    public void open(final Player player) {
        if (this.inventory == null && this.inventoryHolder == null) {
            this.inventory = Bukkit.createInventory(null, this.inventorySlots, Component.text(this.inventoryTitle));
        } else if (this.inventory == null)
            this.inventory = Bukkit.createInventory(this.inventoryHolder, this.inventorySlots, Component.text(this.inventoryTitle));

        this.clickableItems.keySet().forEach((slotNum) -> {
            this.inventory.setItem(slotNum, this.clickableItems.get(slotNum).build());
        });

        player.openInventory(this.inventory);
    }

    @EventHandler
    public void onCloseEvent(final InventoryCloseEvent event) {
        if (event.getInventory() != this.inventory) return;
        if (this.closeAction == null) return;
        this.closeAction.onClose(new InventoryCloseAction(event));

        HandlerList.unregisterAll(this); // Unregister events when this inventory gets closed
    }

    @EventHandler
    public void onOpenEvent(final InventoryOpenEvent event) {
        if (event.getInventory() != this.inventory) return;
        if (this.openAction == null) return;
        this.openAction.onOpen(new InventoryOpenAction(event));
    }

    @EventHandler
    public void onClickEvent(final InventoryClickEvent event) {
        if (event.getInventory() != this.inventory) return;
        if (this.clickAction == null) return;
        this.clickAction.onClick(new InventoryClickAction(event));

        if (this.clickableItems.get(event.getSlot()) != null) {
            final ClickableItem clickableItem = this.clickableItems.get(event.getSlot());
            clickableItem.guiClickableItem.onItemClick(new ClickableItemAction(event));
        }
    }
}
