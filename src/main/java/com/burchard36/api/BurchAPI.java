package com.burchard36.api;

import com.burchard36.api.command.CommandInjector;
import com.burchard36.api.inventory.GlobalInventoryListener;
import com.burchard36.api.utils.reflections.PackageScanner;
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
    protected GlobalInventoryListener inventoryListener;
    protected PluginJsonWriter jsonWriter;

    @Getter
    protected final ApiSettings apiSettings = new ApiSettings();

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.onPreApiEnable();
        this.jsonWriter = new PluginJsonWriter(new GsonBuilder().setPrettyPrinting().create());

        if (this.getApiSettings().isUseInventoryModule())
            this.inventoryListener = new GlobalInventoryListener(this);

        // Run the Annotation checks and register commands
        // try catch for developmental reasons
        try {
            CommandInjector.injectCommands();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
     * Gets the {@link PluginJsonWriter} instance of this class
     * @return the instance of {@link PluginJsonWriter}
     */
    public final PluginJsonWriter getJsonWriter() {
        return this.jsonWriter;
    }

    /**
     * Returns whether the Api is in debug state, this is generally used for the {@link com.burchard36.api.utils.Logger}
     *
     * If you are extending this class, you can Override this method.
     *
     * @return A {@link Boolean} true if debugging is active
     */
    public boolean isDebug() {
        return true;
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
    public final GlobalInventoryListener getGlobalInventoryListener() {
        return this.inventoryListener;
    }

    /**
     * Gets a new {@link PackageScanner} instance
     * @return A new generic {@link PackageScanner}
     */
    public final <T> PackageScanner<T> newPackageScanner() {
        return new PackageScanner<>();
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
