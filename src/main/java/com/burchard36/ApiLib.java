package com.burchard36;

import com.burchard36.command.ApiCommand;
import com.burchard36.json.PluginDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;

public final class ApiLib implements Api {

    public JavaPlugin plugin;
    private PluginDataManager manager;

    /**
     * Initializes the API to a plugin
     */
    public ApiLib initializeApi(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.manager = new PluginDataManager(plugin);
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

    public PluginDataManager getDataManager() {
        return this.manager;
    }

    @Override
    public PluginDataManager getPluginDataManager() {
        return this.manager;
    }
}
