package com.burchard36.json;

import com.squareup.moshi.JsonWriter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigFile {



    abstract void onSave();

    abstract void onReload();

    abstract void write(final JsonWriter writer);

    abstract Config onLoad();


}
