package com.burchard36.api.data.json;

import lombok.Getter;

import java.io.File;

/**
 * Class to extend classes you want to be specified as Json data classes
 */
@Getter
public class JsonDataFile {

    /**
     * To be changed
     */
    protected transient File file;

    /**
     * Returns this file
     * @return {@link File}
     */
    public final File getFile() {
        return this.file;
    }
}
