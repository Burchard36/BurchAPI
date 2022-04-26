package com.burchard36.api;

import com.burchard36.api.command.CommandInjector;
import com.burchard36.api.inventory.GlobalInventoryListener;
import com.burchard36.api.json.PluginJsonWriter;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Users of this API are expected to extend this class, rather than JavaPlugin,
 * BurchAPI will provide methods for JavaPlugin
 *
 * @author Dalton Burchard
 * @since 2.1.5
 */
public abstract class BurchAPI extends JavaPlugin implements Api {

    public static BurchAPI INSTANCE;
    private GlobalInventoryListener inventoryListener;

    @Getter
    private final ApiSettings apiSettings = new ApiSettings();

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.onPreApiEnable();
        PluginJsonWriter jsonWriter = new PluginJsonWriter(new GsonBuilder().setPrettyPrinting().create());

        if (this.getApiSettings().isUseInventoryModule())
            this.inventoryListener = new GlobalInventoryListener(this);

        // Run the Annotation checks and register commands
        CommandInjector.injectCommands();

        this.onPluginEnable();
    }

    @Override
    public void onDisable() {
        this.onPluginDisable();
    }

    /**
     * Plugins can override this and change certain aspects of the API functionality before
     * api initialization
     *
     * @since 2.1.5
     */
    public abstract void onPreApiEnable();

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

    /**
     * Returns whether the Api is in debug state, this is generally used for the {@link com.burchard36.api.utils.Logger}
     *
     * If you are extending this class, you can Override this method.
     *
     * @return A {@link Boolean} true if debugging is active
     */
    public boolean isDebug() {
        return false;
    }

    /**
     * Returns the logger prefix for {@link com.burchard36.api.utils.Logger}
     *
     * If your class is extending this class, you can Override this method to change the output.
     *
     * @return Any {@link String}
     */
    public String loggerPrefix() {
        return "BurchAPI";
    }

    @Override
    public GlobalInventoryListener getGlobalInventoryListener() {
        return this.inventoryListener;
    }

    /**
     * Converts a normal string to a formatted one
     * @param message Any {@link String} to convert
     * @return Converted String
     * @since 2.1.5
     */
    public static String convert(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Converts a varargs {@link String} to colored {@link List} of {@link String}'s
     * @param message Varargs of any {@link String} to convert to colored format using ampersand color codes
     * @return A {@link List} of colored {@link String}'s (using ampersand color codes)
     * @since 2.1.5
     */
    public static List<String> convert(final String... message) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i <= message.length; i++) {
            list.add(convert(message[i]));
        }
        return list;
    }

}
