package com.burchard36.api.data.annotations;

import com.burchard36.api.data.json.enums.FileType;

/**
 * For testing, not fully implemented yet
 */
public @interface PlayerDataFile {
    /**
     * The filetype to save this PlayerDataFile as
     * @return An enum value from {@link FileType}
     */
    FileType fileType() default FileType.JSON;

    /**
     * Should data of this file be saved asynchronously, or in sync
     *
     * this value is defaulted to true
     *
     * @return A {@link Boolean}, true if you want to run async saving (default true), false if not
     */
    boolean runAsync() default true;
}
