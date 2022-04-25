package com.burchard36.api.json.annotations;

import com.burchard36.api.json.enums.FileType;

public @interface PlayerDataFile {
    FileType fileType() default FileType.JSON;
    boolean runAsync() default true;
}
