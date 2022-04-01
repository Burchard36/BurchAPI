package com.burchard36.json;

import com.burchard36.Logger;
import com.burchard36.json.exceptions.JsonFileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.HashMap;

public class PluginDataMap {

    private final Gson gson;
    private final PluginJsonWriter writer;
    private final HashMap<String, JsonDataFile> dataMapByStrings;

    public PluginDataMap() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.writer = new PluginJsonWriter(this.gson);
        this.dataMapByStrings = new HashMap<>();
    }

    /**
     * Deletes a JsoNDataFile and clears it from the PluginDataMap
     * @param E key to use to delete file
     */
    public void deleteDataFile(final String E) {
        final JsonDataFile file = this.getDataFile(E);

        if (file.getFile().delete()) {
            this.dataMapByStrings.remove(E);
        } else Logger.error("Error when deleting files! Please look into this (Did you reload a plugin?? This is an API level error!!!!!!)");
    }

    /**
     * Loads the Config file into the map while loading its data from file
     * @param E String to set as Key for Config
     * @param dataFile JsonDataFile to set
     */
    public void loadDataFile(final String E, final JsonDataFile dataFile) {

        JsonDataFile data = this.writer.getDataFromFile(dataFile.getFile(), dataFile.getClass());
        if (data == null) {
            this.writer.createFile(dataFile);
            data = this.writer.getDataFromFile(dataFile.getFile(), dataFile.getClass());
            if (data == null) {
                Logger.error("YO DUDE!! This file doesnt exists! This is an API Level error, you seriously fucked up or you need to contact the developer because maybe i fucked up. Sorry!");
                return;
            }
        }
        data.file = dataFile.getFile(); // We need to set the file again because Gson doesnt call the constructor to get the File
        this.dataMapByStrings.putIfAbsent(E, data);
    }

    /**
     * Gets data from the map by a string hats cached
     * @param E String key to find the data by
     * @return JsonDataFile instance
     */
    public JsonDataFile getDataFile(final String E) {
        return this.dataMapByStrings.get(E);
    }

    /**
     * Returns the DataMap tht organized by String objects
     * @return HashMap of jsonDataFile's that were loaded by strings
     */
    public HashMap<String, JsonDataFile> getDataMapByStrings() {
        return this.dataMapByStrings;
    }

    /**
     * Saves a specific JsonDataFile
     * @param dataFile String value of data file to save
     */
    public final void saveDataFile(final String dataFile) {
        final JsonDataFile jsonDataFile = this.getDataFile(dataFile);
        if (jsonDataFile == null) {
            new JsonFileNotFoundException("Tried to find data file by key for saving, but could not find it! Data key: " + dataFile).printStackTrace();
            return;
        }

        this.writer.writeDataToFile(jsonDataFile);
    }

    /**
     * Saves all the objects inside the HashMaps
     */
    public final void saveAll() {
        this.getDataMapByStrings().values().forEach(this.writer::writeDataToFile);
    }

    /**
     * gets all the data files in this HashMap
     * @return Collection of JsonDataFiles stores in this PluginDataMap
     */
    public final Collection<JsonDataFile> getDataFiles() {
        return this.dataMapByStrings.values();
    }

    /**
     * Reloads a config file
     * @param E String object to use
     */
    public void reloadDataFile(final String E) {
        final JsonDataFile dataFile = this.getDataFile(E);
        if (dataFile == null) {
            new JsonFileNotFoundException("Could not find file when reload JsonDataFile! Please ensure this exists! JsonDataFile key: " + E).printStackTrace();
            return;
        }
        this.getDataMapByStrings().replace(E, this.reload(dataFile));
    }

    /**
     * Reloads a specific JsonDataFile by object
     * @param dataFile JsonDataFile to reload
     * @return JsonDataFile instance that is reloaded from file.
     */
    private JsonDataFile reload(final JsonDataFile dataFile) {
        this.writer.writeDataToFile(dataFile);
        return this.writer.getDataFromFile(dataFile.getFile(), dataFile.getClass());
    }
}
