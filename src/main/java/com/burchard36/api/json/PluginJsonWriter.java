package com.burchard36.api.json;

import com.burchard36.api.utils.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

/**
 * A Json writer, uses class serialization to write/read from files from {@link Gson}
 */
public record PluginJsonWriter(Gson gson) {

    /**
     * Asynchronously/Synchronously creates a {@link JsonDataFile}
     * @param dataFile A {@link JsonDataFile} to create
     * @return {@link CompletableFuture<File>}
     */
    public CompletableFuture<File> createFile(final JsonDataFile dataFile) {
        final File file = dataFile.getFile();
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    if (file.getParentFile().mkdirs()){
                        Logger.log("&aAPI :: Successfully created directories");
                    }
                if (file.createNewFile()) {
                    Logger.log("&aAPI :: Successfully created a DataFile!");
                    String jsonString = this.gson.toJson(dataFile);
                    Writer writer = new FileWriter(file);
                    writer.append(jsonString);
                    writer.close();
                    Logger.log("&aAPI :: Successfully wrote default data for DataFile");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return CompletableFuture.completedFuture(file);
            }
        }
        return CompletableFuture.completedFuture(file);
    }

    /**
     * Writes data to a file asynchronously
     * @param config class extending JsonDataFile to save
     * @return true when successfully, false if error (If this method gets a File that does not exist, this method will always return true)
     */
    public CompletableFuture<Boolean> writeDataToFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) {
            this.createFile(config).thenAcceptAsync((createdFile) -> {
                Logger.debug("Created file async");
            });
            return CompletableFuture.completedFuture(true);
        } else {
            try {
                final Writer writer = new FileWriter(file);
                this.gson.toJson(config, writer);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return CompletableFuture.completedFuture(false);
            }
        }

        Logger.debug("Successfully saved a data file");
        return CompletableFuture.completedFuture(true);
    }

    /**
     * Gets a data from a file
     * @param file {@link File} A file
     * @param clazz {@link Class}  The Class file of the data you are trying to get
     * @return instance of your class extending {@link JsonDataFile}
     */
    public JsonDataFile getDataFromFile(final File file, Class<? extends JsonDataFile> clazz) {
        if (!file.exists()) return null;
        try {
            return this.gson.fromJson(Files.newBufferedReader(Paths.get(file.toURI())), clazz);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
