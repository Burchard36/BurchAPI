package com.burchard36.json;

import java.io.File;
import java.net.URI;

public class JsonDataFile {

    public transient File file;

    public JsonDataFile(final File file) {
        this.file = file;
    }

    public JsonDataFile(final URI fileUri) {
        this.file = new File(fileUri);
    }

    public final File getFile() {
        return this.file;
    }
}
