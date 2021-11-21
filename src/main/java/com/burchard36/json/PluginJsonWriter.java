package com.burchard36.json;

import com.burchard36.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public record PluginJsonWriter(Gson gson) {

    public File createFile(final JsonDataFile dataFile) {
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
                    Logger.log(jsonString);
                    Writer writer = new FileWriter(file);
                    writer.append(jsonString);
                    writer.close();
                    Logger.log("&aAPI :: Successfully wrote default data for DataFile");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return file;
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
