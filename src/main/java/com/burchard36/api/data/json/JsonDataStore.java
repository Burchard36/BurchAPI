package com.burchard36.api.data.json;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.PluginJsonWriter;
import com.burchard36.api.data.FileDataStore;
import com.burchard36.api.data.annotations.DataStoreID;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@DataStoreID(id = "internal_jsonDataStore")
public class JsonDataStore implements FileDataStore, Listener {

    protected final BurchAPI instance;
    public HashMap<Enum<?>, JsonDataFile> dataFiles;
    protected PluginJsonWriter writer;

    public JsonDataStore(BurchAPI api) {
        this.instance = api;
    }

    @Override
    public void onEnable() {
        this.dataFiles = new HashMap<>();
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {

    }

    protected String asFile(UUID uuid) {
        return uuid.toString() + ".json";
    }

    @Override
    public List<Class<? extends JsonDataFile>> dataFiles() {
        return List.of(JsonPlayerDataFile.class);
    }

    @Override
    public BukkitTask autoSaveTask() {
        return null;
    }
}
