package com.burchard36.api.command;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.command.exceptions.CommandExceptionFactory;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.command.annotation.*;
import com.burchard36.api.utils.reflections.PackageScanner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Injects ApiCommands into Bukkit's command map
 */
public class CommandInjector extends CommandExceptionFactory {

    /**
     * Injects all commands into the BukkitCommand map
     *
     * Also search's for classes extending {@link ApiCommand} and auto-registers them if they have
     * {@link CommandName} or {@link RegisterCommand} Annotation(s)
     */
    public static void injectCommands() {
        final PackageScanner<ApiCommand> scanner = BurchAPI.INSTANCE.getPackageScanner();
        scanner.subclassSearchQuery(BurchAPI.INSTANCE.getClass().getPackage(), ApiCommand.class)
                .execute();
        HashMap<Class<? extends Annotation>, Class<? extends ApiCommand>> classes =
                scanner.findWithClassConstructorAnnotations(RegisterCommand.class, CommandName.class);


        Logger.debug("Attempting to inject: " + classes.size() + " ApiCommand's");
        for (final Class<? extends ApiCommand> clazz : classes.values()) {

            if (BurchAPI.INSTANCE.getApiSettings().getCommandAutoRegisterBlacklist().contains(clazz)) {
                Logger.debug("Not loading: " + clazz.getName() + " because it was on the Command registration black-list");
                continue;
            }

            PackageScanner.InvocationResult<ApiCommand, PackageScanner.InvocationErrorStatus> result =
                    scanner.invokeClass(clazz);

            if (!result.wasSuccessfullyInvoked()) {
                switch (result.getValue()) {
                    case ERR_INVALID_CONSTRUCTOR -> throw newConstructorNotFoundException("Could not locate a valid constructor for the class: " + clazz.getName() +
                            " when loading it as an ApiCommand! Please ensure the Constructor has public access!");
                    case ERR_INVOCATION -> throw newCommandInvocationException("Could not invoke the ApiCommand class: " + clazz.getName());
                }
                continue;
            }

            ApiCommand command = result.getKey();

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
                    throw newInvalidCommandAnnotationException("The class: " + command.getClass().getName() + " did not have a valid command name set!");
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
    protected static void register(final Command command) {
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
