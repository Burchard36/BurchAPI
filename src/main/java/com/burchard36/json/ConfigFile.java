package com.burchard36.json;

import com.squareup.moshi.JsonWriter;

public abstract class ConfigFile {



    abstract void onSave();

    abstract void onReload();

    abstract void write(JsonWriter writer);

    abstract Config onLoad();


}
