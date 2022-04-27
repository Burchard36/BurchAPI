package com.burchard36.api.data.json;

import com.burchard36.api.BurchAPI;
import lombok.Getter;

import java.io.File;
import java.util.UUID;

/**
 * End users should extend this class to allow more data to be saved
 */
public class JsonPlayerDataFile implements JsonDataFile {

    @Getter
    protected transient UUID uuid;

    protected void provideUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public File getFile() {
        return new File(BurchAPI.INSTANCE.getDataFolder(),
                String.format("/player-data/%s.json", this.uuid.toString()));
    }
}
