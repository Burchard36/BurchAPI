package com.burchard36.api.data.json.writer;

import com.burchard36.api.data.json.JsonDataFile;
import com.burchard36.api.data.json.writer.exceptions.JsonWriterExceptionFactory;
import com.burchard36.api.utils.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A Json writer, uses class serialization to write/read from files from {@link Gson}
 */
public class PluginJsonWriter extends JsonWriterExceptionFactory {

    protected final Gson gson;

    public PluginJsonWriter(Gson gson) {
        this.gson = gson;
    }

    /**
     * Creates a file if it doesnt exist, gets it if not
     * @param file The {@link JsonDataFile} you are wanting to load
     * @return An {@link Optional<JsonDataFile>} will be provided if no errors occur when creating/getting the file
     */
    public final CompletableFuture<Optional<JsonDataFile>> createIfNotExistGetIfNot(final JsonDataFile file) {
        final File jsonFile = file.getFile();
        return this.createFile(file);
    }

    /**
     * Asynchronously/Synchronously creates a {@link JsonDataFile}
     * <br>
     * This will ONLY create a new file if one doesn't exist,
     *
     * @param dataFile A {@link JsonDataFile} to create
     * @return {@link CompletableFuture<Boolean>} true if new file was created, false if not.
     */
    public CompletableFuture<Optional<JsonDataFile>> createFile(final JsonDataFile dataFile) {
        final File file = dataFile.getFile();
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    if (file.getParentFile().mkdirs()){
                        Logger.log(":: &aSuccessfully created directories");
                    }
                if (file.createNewFile()) {
                    String jsonString = this.gson.toJson(dataFile);
                    Writer writer = new FileWriter(file);
                    writer.append(jsonString);
                    writer.close();
                    return this.getDataFromFile(dataFile.getFile(), dataFile.getClass());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return CompletableFuture.completedFuture(Optional.empty());
            }
        }
        return this.getDataFromFile(file, dataFile.getClass());
    }

    /**
     * Writes data to a file asynchronously
     * @param config class extending JsonDataFile to save
     * @return true when successfully, false if error (If this method gets a File that does not exist, this method will always return true)
     */
    public CompletableFuture<Boolean> writeDataToFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) {
            this.createFile(config).thenAcceptAsync((createdFile) -> Logger.debug("Created file async"));
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

        return CompletableFuture.completedFuture(true);
    }

    /**
     * Gets a data from a file
     * @param file {@link File} A file
     * @param clazz {@link Class}  The Class file of the data you are trying to get
     * @return instance of your class extending {@link JsonDataFile}
     */
    public CompletableFuture<Optional<JsonDataFile>> getDataFromFile(final File file, Class<? extends JsonDataFile> clazz) {
        if (!file.exists()) return CompletableFuture.completedFuture(null);
        try {
            return CompletableFuture.completedFuture(Optional.of(this.gson.fromJson(Files.newBufferedReader(Paths.get(file.toURI())), clazz)));
        } catch (IOException ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    public final CompletableFuture<Boolean> deleteDataFile(final JsonDataFile dataFile) {
        if (!dataFile.getFile().exists()) return CompletableFuture.completedFuture(false);
        if (dataFile.getFile().delete()) return CompletableFuture.completedFuture(true);
        else return CompletableFuture.completedFuture(false);
    }
}
