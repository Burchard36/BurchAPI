package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import com.burchard36.api.command.CommandInjector;
import com.burchard36.api.command.annotation.*;
import com.burchard36.api.command.exceptions.CommandConstructorNotFoundException;
import com.burchard36.api.command.exceptions.InvalidCommandAnnotationException;
import com.burchard36.api.inventory.GlobalInventoryListener;
import com.burchard36.api.json.PluginJsonWriter;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Users of this API are expcted to extend this class, rather than JavaPlugin, BurchAPI will provide methods for JavaPlugin
 */
public abstract class BurchAPI extends JavaPlugin implements Api {

    public static BurchAPI INSTANCE;
    private GlobalInventoryListener inventoryListener;
    private PluginJsonWriter jsonWriter;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.jsonWriter = new PluginJsonWriter(new GsonBuilder().setPrettyPrinting().create());
        this.inventoryListener = new GlobalInventoryListener(this);

        /** Run the Annotation checks and register commands */
        CommandInjector.injectCommands();

        this.onPluginEnable();
    }

    @Override
    public void onDisable() {
        this.onPluginDisable();
    }

    /**
     * Initializes the API to a plugin
     */
    public BurchAPI initializeApi(final JavaPlugin plugin) {

        return this;
    }

    /**
     * Checks if a JavaPlugin implements the Api class, and if it does pull the Api
     * @param plugin class extending JavaPlugin and
     * @return Returns Api instance if JavaPlugin implements Api class, returns null if it doesnt
     */
    public static Api getLib(final JavaPlugin plugin) {
        if (plugin instanceof Api) {
            return (Api) plugin;
        } else return null;
    }

    public boolean isDebug() {
        return false;
    }

    public String loggerPrefix() {
        return "BurchAPI";
    }

    @Override
    public GlobalInventoryListener getGlobalInventoryListener() {
        return this.inventoryListener;
    }

    /**
     * Converts a normal string to a formatted one
     * @param message String to convert
     * @return Converted String
     */
    public static String convert(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Converts a varargs string to colored List of strings
     * @param message String to convert to colored
     * @return List of colored String (using '&' color codes)
     */
    public static List<String> convert(final String... message) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i <= message.length; i++) {
            list.add(convert(message[i]));
        }
        return list;
    }

}
