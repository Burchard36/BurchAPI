package com.burchard36.json;

import com.burchard36.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public record PluginJsonWriter(ObjectMapper mapper) {

    public File createFile(final JsonDataFile config) {
        final File file = config.getFile();
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    if (file.getParentFile().mkdirs())
                        Logger.log("&aAPI :: Successfully created directories");
                if (file.createNewFile()) this.mapper.writeValue(file, config);

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
            this.mapper.writeValue(file, config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public JsonDataFile getDataFromFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) file = this.createFile(config);

        try {
            return this.mapper.readValue(file, config.getClass());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
