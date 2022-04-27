package com.burchard36.api.data.json;

import com.burchard36.api.BurchAPI;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

/**
 * JsonData classes should extend this class
 */
public abstract class JsonPlayerDataFile extends JsonDataFile {

    /**
     * The player to use for this File
     * @param player Player object to load/save data against
     */
    public JsonPlayerDataFile(Player player) {
        super();
        this.file = new File(BurchAPI.INSTANCE.getDataFolder(),
                this.directoryPath() +
                this.playerUUID().toString() + ".json");
    }



    abstract UUID playerUUID();
    abstract String directoryPath();
}
