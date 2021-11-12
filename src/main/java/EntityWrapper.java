import com.burchard36.ApiLib;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;

import static com.burchard36.ApiLib.convert;

public class EntityWrapper {

    private final Entity entity;

    public EntityWrapper(final Entity entity) {
        this.entity = entity;
    }

    public final void setHologram(final String message) {
        this.entity.setCustomNameVisible(true);
        this.entity.setCustomName(convert(message));
    }

    public final void setStringValue(final String key, final String value) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        container.set(nameKey, PersistentDataType.STRING, value);
    }

    public final void setIntegerValue(final String key, final int value) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        container.set(nameKey, PersistentDataType.INTEGER, value);
    }

    public final String getStringValue(final String key) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return container.get(nameKey, PersistentDataType.STRING);
    }

    public final Integer getIntegerValue(final String key) {
        final PersistentDataContainer container = this.entity.getPersistentDataContainer();
        final NamespacedKey nameKey = new NamespacedKey(ApiLib.INSTANCE, key);
        return container.get(nameKey, PersistentDataType.INTEGER);
    }

    public Entity getEntity() {
        return this.entity;
    }
}
