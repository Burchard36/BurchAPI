package com.burchard36.api.utils;

import com.burchard36.api.BurchAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * A simple wrapper to easily modify the data of a {@link Entity}
 */
public class EntityWrapper {

    private final Entity entity;

    /**
     * Initialized this class with a {@link Entity}
     * @param entity A {@link Entity}
     */
    public EntityWrapper(final Entity entity) {
        this.entity = entity;
    }

    /**
     * Sets the name above the linked entities head
     * @param message A {@link String} Colors automatically get parsed here if using the ampersand symbol
     */
    public final void setHologram(final String message) {
        this.entity.setCustomNameVisible(true);
        this.entity.setCustomName(BurchAPI.convert(message, null));
    }

    /**
     * Sets a {@link String} value for the linked entity {@link PersistentDataContainer}
     * @param key A {@link String} key to store the value as
     * @param value A {@link String} value to store
     */
    public final void setStringValue(final String key, final String value) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        container.set(nameKey, PersistentDataType.STRING, value);
    }

    /**
     * Sets a {@link Integer} value for the linked entity
     * @param key A {@link String} key to store the value as
     * @param value A {@link Integer} value to store
     */
    public final void setIntegerValue(final String key, final int value) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        container.set(nameKey, PersistentDataType.INTEGER, value);
    }

    /**
     * Gets a {@link String} value from the linked entities {@link PersistentDataContainer}
     * @param key A {@link String} key to search for the value under
     * @return A {@link String} from the {@link PersistentDataContainer}, value will be null if none found
     */
    public final String getStringValue(final String key) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        return container.get(nameKey, PersistentDataType.STRING);
    }

    /**
     * Gets a {@link Integer} value from the linked entities {@link PersistentDataContainer}
     * @param key A {@link String} key to search for the value under
     * @return A {@link Integer} from the {@link PersistentDataContainer}, value will be null if none found
     */
    public final Integer getIntegerValue(final String key) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(BurchAPI.INSTANCE, key);
        return container.get(nameKey, PersistentDataType.INTEGER);
    }

    /**
     * Returns the {@link  Entity} of this class
     * @return The {@link Entity} of this class
     */
    public Entity getEntity() {
        return this.entity;
    }
}
