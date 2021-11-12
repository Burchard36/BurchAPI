package com.burchard36;

import com.burchard36.command.ApiCommand;
import com.burchard36.hologram.HologramManager;
import com.burchard36.json.PluginDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;

public final class ApiLib implements Api {

    public JavaPlugin plugin;
    public static JavaPlugin INSTANCE;
    private PluginDataManager manager;
    private HologramManager hologramManager;

    /**
     * Initializes the API to a plugin
     */
    public ApiLib initializeApi(final JavaPlugin plugin) {
        this.plugin = plugin;
        INSTANCE = plugin;
        this.manager = new PluginDataManager(plugin);
        this.hologramManager = new HologramManager();
        return this;
    }

    /**
     * Registers a single command
     * @param command Command extending ApiCommand
     * @return instance of ApiLib
     */
    public ApiLib registerCommand(final ApiCommand command) {
        this.register(command);
        return this;
    }

    /**
     * Registers a whole list of commands at once
     * @param commands List of classes extending ApiCommand
     * @return instance of this class
     */
    public ApiLib registerCommands(List<ApiCommand> commands) {
        commands.forEach(this::register);
        return this;
    }

    /**
     * Injects ApiCommand into Bukkits CommandMap
     * @param command Class extending BukkitCommand to inject into CommandMap
     */
    private void register(final ApiCommand command) {
        try {
            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            final CommandMap commandMap = ((CommandMap) commandMapField.get(Bukkit.getServer()));
            commandMap.register(command.getName(), command);
        } catch (final IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if a JavaPlugin implements the Api class, and if it does pull the Api
     * @param plugin
     * @return Returns Api instance if JavaPlugin implements Api class, returns null if it doesnt
     */
    public static Api getLib(final JavaPlugin plugin) {
        if (plugin instanceof Api) {
            return (Api) plugin;
        } else return null;
    }

    @Override
    public PluginDataManager getPluginDataManager() {
        return this.manager;
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
     * Returns an instance of the Hologram Manager
     * @return instance of HologramManager
     */
    public HologramManager getHologramManager() {
        return this.hologramManager;
    }
}
