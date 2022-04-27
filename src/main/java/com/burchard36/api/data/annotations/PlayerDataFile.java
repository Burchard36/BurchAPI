package com.burchard36.api.data.annotations;

import com.burchard36.api.data.json.enums.FileType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For testing, not fully implemented yet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
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
