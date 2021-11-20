package com.burchard36.json;

import com.burchard36.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public record PluginJsonWriter(Gson gson) {

    public File createFile(final JsonDataFile config) {
        final File file = config.getFile();
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    if (file.getParentFile().mkdirs())
                        Logger.log("&aAPI :: Successfully created directories");
                if (file.createNewFile()) this.gson.toJson(config, new FileWriter(file));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return config.getFile();
    }

    public void writeDataToFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) file = this.createFile(config);

        try {
            this.gson.toJson(config, new FileWriter(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public JsonDataFile getDataFromFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) file = this.createFile(config);

        try {
            return this.gson.fromJson(Files.newBufferedReader(Paths.get(file.toURI())), config.getClass());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
