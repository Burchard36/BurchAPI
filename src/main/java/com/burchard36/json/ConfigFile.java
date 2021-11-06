package com.burchard36.json;

import com.burchard36.json.events.JsonLoadEvent;
import com.burchard36.json.events.JsonSaveEvent;
import com.squareup.moshi.JsonWriter;

import java.io.File;

public abstract class ConfigFile {



    abstract void onSave(JsonSaveEvent jsonSaveEvent);
    abstract void onLoad(JsonLoadEvent jsonLoadEvent);

    abstract void onReload();

    abstract void save();
    abstract Config load();

    abstract void onWrite(JsonWriter writer);
    abstract void onRead(final Config config);

    abstract File getFile();


}
