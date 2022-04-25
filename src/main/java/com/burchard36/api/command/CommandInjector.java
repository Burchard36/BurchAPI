package com.burchard36.api.command;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.command.annotation.*;
import com.burchard36.api.command.exceptions.CommandConstructorNotFoundException;
import com.burchard36.api.command.exceptions.InvalidCommandAnnotationException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Injects ApiCommands into Bukkits command map
 */
public class CommandInjector {

    public static void injectCommands() {
        Reflections reflections = new Reflections(BurchAPI.INSTANCE.getClass().getPackage().getName());

        Set<Class<? extends ApiCommand>> classes = reflections.getSubTypesOf(ApiCommand.class);
        for (final Class<? extends ApiCommand> clazz : classes) {

            if (BurchAPI.INSTANCE.getApiSettings().getCommandAutoRegisterBlacklist().contains(clazz)) continue;

            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = null;
            Object toProvide = null;
            try {
                for (Constructor<?> aConstructor : constructors) {
                    if (aConstructor.getParameterTypes().length == 0 && constructor == null)
                        constructor = clazz.getDeclaredConstructor();

                    if (aConstructor.getParameterTypes().length == 1 && aConstructor.getParameterTypes()[0] == BurchAPI.INSTANCE.getClass()) {
                        toProvide = BurchAPI.INSTANCE;
                        constructor = clazz.getDeclaredConstructor(BurchAPI.INSTANCE.getClass());
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


            CommandInjector.registerCommand(command);
            Logger.log("Registered command class: " + command.getClass().getName());
        }
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
        commands.forEach(CommandInjector::register);
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
}
