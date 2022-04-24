package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import com.burchard36.api.command.annotation.*;
import com.burchard36.api.command.exceptions.CommandConstructorNotFoundException;
import com.burchard36.api.command.exceptions.InvalidCommandAnnotationException;
import com.burchard36.api.inventory.GlobalInventoryListener;
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

public abstract class BurchAPI extends JavaPlugin implements Api {

    public static JavaPlugin INSTANCE;
    private GlobalInventoryListener inventoryListener;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.inventoryListener = new GlobalInventoryListener(this);
        Logger.init(getLib(this));

        Reflections reflections = new Reflections(this.getClass().getPackage().getName());

        Set<Class<? extends ApiCommand>> classes = reflections.getSubTypesOf(ApiCommand.class);
        for (final Class<?> clazz : classes) {

            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = null;
            Object toProvide = null;
            try {
                for (Constructor<?> aConstructor : constructors) {
                    if (aConstructor.getParameterTypes().length == 0 && constructor == null)
                        constructor = clazz.getDeclaredConstructor();

                    if (aConstructor.getParameterTypes().length == 1 && aConstructor.getParameterTypes()[0] == this.getClass()) {
                        toProvide = this;
                        constructor = clazz.getDeclaredConstructor(this.getClass());
                    }
                }
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }

            if (constructor == null)
                throw new CommandConstructorNotFoundException("No default, or constructor with JavaPlugin found for class" +
                        clazz.getName() + " please review your API usage to make sure you class has a valid constructor!");

            ApiCommand command;
            try {
                try {
                    command = (ApiCommand) constructor.newInstance(toProvide);
                } catch (NullPointerException ignored) {
                    command = (ApiCommand) constructor.newInstance();
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                ex.printStackTrace();
                continue;
            }

            final RegisterCommand commandAnnotation = command.getClass().getAnnotation(RegisterCommand.class);
            final CommandName commandName = command.getClass().getAnnotation(CommandName.class);
            final CommandDescription commandDescription = command.getClass().getAnnotation(CommandDescription.class);
            final CommandUsage commandUsage = command.getClass().getAnnotation(CommandUsage.class);
            final CommandAliases commandAliases = command.getClass().getAnnotation(CommandAliases.class);

            if (commandAnnotation != null) {

                if (!commandAnnotation.name().equalsIgnoreCase(""))
                    command.setCommandName(commandAnnotation.name());

                if (!commandAnnotation.description().equalsIgnoreCase(""))
                    command.setDescription(commandAnnotation.description());

                if (!commandAnnotation.usageMessage().equalsIgnoreCase(""))
                    command.setUsage(commandAnnotation.usageMessage());

                if (commandAnnotation.aliases().length != 0)
                    command.setAliases(Arrays.asList(commandAnnotation.aliases()));
            } else {
                if (commandName != null && !commandName.name().equalsIgnoreCase("")) {
                    command.setCommandName(commandName.name());
                } else
                    throw new InvalidCommandAnnotationException("The class: " + command.getClass().getName() + " did not have a valid command name set!");
            }


            if (commandDescription != null && !commandDescription.description().equalsIgnoreCase(""))
                command.setDescription(commandDescription.description());


            if (commandUsage != null && !commandUsage.usageMessage().equalsIgnoreCase(""))
                command.setUsage(commandUsage.usageMessage());

            if (commandAliases != null && commandAliases.aliases().length > 0)
                command.setAliases(Arrays.asList(commandAliases.aliases()));


            registerCommand(command);
            Logger.log("Registered command class: " + command.getClass().getName());
        }

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
     * Registers a single command
     * @param command Command extending ApiCommand
     */
    public static void registerCommand(final ApiCommand command) {
        register(command);
    }

    /**
     * Registers a whole list of commands at once
     * @param commands List of classes extending ApiCommand
     */
    public static void registerCommands(List<ApiCommand> commands) {
        commands.forEach(BurchAPI::register);
    }

    /**
     * Injects ApiCommand into Bukkits CommandMap
     * @param command Class extending BukkitCommand to inject into CommandMap
     */
    private static void register(final Command command) {
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
