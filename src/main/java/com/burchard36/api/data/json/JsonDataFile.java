package com.burchard36.api.data.json;

import java.io.File;

/**
 * Class to extend classes you want to be specified as Json data classes
 */
public interface JsonDataFile {

    /**
     * The {@link File} to save this json data to
     * @return The {@link File} to save this data class to
     */
    File getFile();
}
