package com.burchard36.api.inventory;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.inventory.interfaces.GuiClickAction;
import com.burchard36.api.inventory.interfaces.GuiCloseAction;
import com.burchard36.api.inventory.interfaces.GuiOpenAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.burchard36.api.BurchAPI.convert;

/**
 * The API class to create the APIs PluginInventorys
 */
public class PluginInventory {

    private int inventorySlots;
    private String inventoryName;
    /**
     * The {@link InventoryHolder} of this inventory
     */
    public final InventoryHolder inventoryHolder;
    private Inventory inventory;
    /**
     * The {@link GuiCloseAction} for this inventory
     */
    public GuiCloseAction closeAction;
    /**
     * The {@link GuiOpenAction} for this inventory
     */
    public GuiOpenAction openAction;
    /**
     * The {@link GuiClickAction} for this inventory
     */
    public GuiClickAction clickAction;
    /**
     * The HashMap containing all the {@link ClickableItem} items of this {@link PluginInventory}, ordered by zero-based slot counts
     */
    public final HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();

    /**
     * Created a PluginInventory class
     * @param slots amount of slots for the inventory to have, multiples of 9, no higher than 54
     * @param name name for inventory to have, supports ampersand color codes
     */
    public PluginInventory(final int slots, final String name) {
        this.inventorySlots = slots;
        this.inventoryName = convert(name, null);
        UUID inventoryUuid = UUID.randomUUID();
        this.inventoryHolder = new PluginHolder(inventoryUuid, null);
        this.inventory = Bukkit.createInventory(this.inventoryHolder, slots, convert(name, null));
    }

    /**
     * Sets the inventory display name
     * @param displayName Display name to re-name the inventory, supports ampersand color codes
     * @return instance of this class
     */
    public PluginInventory setDisplayName(final String displayName) {
        this.inventoryName = convert(displayName, null);
        this.inventory = Bukkit.createInventory(this.inventoryHolder, this.inventorySlots, this.inventoryName);
        return this;
    }

    /**
     * Sets the inventory size of this Inventory, if setInventoryHolder gets called this method has no effect
     * @param size Size of this inventory to set it to
     * @return instance of this class
     */
    public PluginInventory setInventorySize(final int size) {
        this.inventorySlots = size;
        this.inventory = Bukkit.createInventory(this.inventoryHolder, this.inventorySlots, this.inventoryName);
        return this;
    }

    /**
     * Sets the inventory type of this inventory, has priority over the inventory size
     * @param type InventoryType to set this Inventory to
     * @return instance of this class
     */
    public PluginInventory setInventoryType(final InventoryType type) {
        this.inventory = Bukkit.createInventory(this.inventoryHolder, type, this.inventoryName);
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
    public PluginInventory setClickableItemAtSlot(final int slot, final ClickableItem item) {
        this.clickableItems.put(slot, item);
        return this;
    }

    /**
     * Ads a {@link List} of {@link ClickableItem}'s to this {@link PluginInventory}
     * @param items A {@link List} of {@link ClickableItem}'s
     * @return instance of this {@link PluginInventory}
     */
    public PluginInventory addClickableItems(final List<ClickableItem> items) {
        int x = 0;
        for (final ClickableItem item : items) {
            this.clickableItems.put(x, item);
            x++;
        }
        return this;
    }

    /**
     * Fills an inventory with a specific ClickableItem
     * @param item ClickableItem to click
     * @param overwrite if true, it will overwrite an existing item there if it exists
     * @return instance of this class
     */
    public PluginInventory fillWith(final ClickableItem item, final boolean overwrite) {
        for (int x = 0; x <= (this.inventory.getSize() - 1); x++) {
            if (!overwrite) if (this.inventory.getItem(x) != null) continue;
            this.setClickableItemAtSlot(x, item);
        }
        return this;
    }

    /**
     * File this {@link PluginInventory} with a {@link List} of {@link ClickableItem}'s
     * @param items A {@link List} of {@link ClickableItem}'s
     * @param overwrite A {@link Boolean}, if true all previously set {@link ClickableItem}'s will be overwritten
     * @return instance of this {@link PluginInventory}
     */
    public PluginInventory fillWith(final List<ClickableItem> items, final boolean overwrite) {
        for (int x = 0; x <= (this.inventory.getSize() - 1); x++) {
            if (!overwrite) if (this.inventory.getItem(x) != null) continue;
            try {
                this.setClickableItemAtSlot(x, items.get(x));
            } catch (final IndexOutOfBoundsException ex) {
                Logger.warn("IndexOutOfBounds exception encountered! This is an API level error please contact a developer");
            }
        }
        return this;
    }

    /**
     * Opens an inventory to a Player
     * @param player Player to open inventory to
     */
    public void open(final Player player) {
        this.inventory = /* Parse placeholders in the Inventory Title for provided player */
                Bukkit.createInventory(this.inventoryHolder,
                this.inventorySlots,
                convert(this.inventoryName, player));
        this.clickableItems.keySet().forEach((slotNum) -> {
            this.inventory.setItem(slotNum, this.clickableItems.get(slotNum).build());
        });

        BurchAPI.INSTANCE.getGlobalInventoryListener().queuePluginInventory(this);
        player.openInventory(this.inventory);
    }

    /**
     * The {@link InventoryHolder} that {@link PluginInventory}'s have
     */
    public static class PluginHolder implements InventoryHolder {

        /**
         * The Identifier of this {@link InventoryHolder}
         */
        public final UUID uuid;
        private Inventory inventory;

        /**
         *
         * @param uuid {@link UUID} uuid identifier of this holder
         * @param inventory {@link Inventory} The Bukkit Inventory
         */
        public PluginHolder(final UUID uuid,
                            Inventory inventory) {
            this.uuid = uuid;
            this.inventory = inventory;
        }

        /**
         * Sets the inventory
         * @param inventory {@link Inventory}
         * @return Instance of this {@link PluginHolder} class
         */
        public PluginHolder setInventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        @Override
        @Nonnull
        public Inventory getInventory() {
            return this.inventory;
        }
    }
}