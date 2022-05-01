package com.burchard36.api.command;

import com.burchard36.api.command.actions.ConsoleSendCommand;
import com.burchard36.api.command.actions.PlayerSendCommand;
import com.burchard36.api.command.actions.PlayerTabComplete;
import com.burchard36.api.command.interfaces.OnConsoleSender;
import com.burchard36.api.command.interfaces.OnPlayerSender;
import com.burchard36.api.command.interfaces.OnSubArgument;
import com.burchard36.api.command.interfaces.OnTabComplete;
import com.burchard36.api.utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The class you want to use when registering commands.
 *
 * You are meant to extend this class
 *
 * @author Dalton Burchard
 * @since 2.1.5
 */
public class ApiCommand extends Command implements TabCompleter {

    protected OnPlayerSender onPlayerSender = null;
    protected OnConsoleSender onConsoleSender = null;
    protected OnTabComplete onTabComplete = null;
    protected final HashMap<String, List<ApiCommandArgument>> subArgumentMap;
    protected OnSubArgument onPlayerSubArgument = null;
    protected String basePermission;

    public ApiCommand() {
        super("");
        this.subArgumentMap = new HashMap<>();
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel,
                           @Nonnull String[] args) {
        final List<String> newArgs = Arrays.asList(args);

        if (sender instanceof final Player player) {
            if (this.onPlayerSender == null) return false;
            this.onPlayerSender.onPlayerSender(new PlayerSendCommand(player, newArgs));
        } else {
            final ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
            if (this.onConsoleSender == null) return false;
            this.onConsoleSender.onConsoleSender(new ConsoleSendCommand(consoleSender, newArgs));
        }

        return false;
    }

    /**
     * Sets the function to run when a {@link Player} sends a command
     * @param sender A {@link OnPlayerSender} lambda interface
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand onPlayerSender(OnPlayerSender sender) {
        this.onPlayerSender = sender;
        return this;
    }

    public ApiCommand subArgument(String args,
                                  @Nullable String permission,
                                  OnSubArgument argument) {
        List<String> splitArgs = new ArrayList<>(List.of(args.split(" ")));
        String theKey = splitArgs.get(0);

        ApiCommandArgument argumentToInsert;

        if (splitArgs.size() == 1) {
            this.subArgumentMap.putIfAbsent(theKey, new ArrayList<>());
            final List<ApiCommandArgument> arguments = this.subArgumentMap.get(theKey);
            argumentToInsert = new ApiCommandArgument(null, permission, argument);
            this.attemptToInsert(argumentToInsert, arguments);
            return this;
        } else if (splitArgs.size() == 2) {
            this.subArgumentMap.putIfAbsent(theKey, new ArrayList<>());
            final List<ApiCommandArgument> arguments = this.subArgumentMap.get(theKey);
            argumentToInsert  = new ApiCommandArgument(splitArgs.get(1), permission, argument);
            this.attemptToInsert(argumentToInsert, arguments);
            return this;
        } else {
            String argumentForRegistry = splitArgs.get(splitArgs.size() - 1);
            argumentToInsert = new ApiCommandArgument(argumentForRegistry, permission, argument);
            splitArgs.remove(splitArgs.size() - 1);
            splitArgs.remove(0);
            String newArgs = String.join(" ", splitArgs);
            this.subArgumentMap.putIfAbsent(newArgs, new ArrayList<>());
            final List<ApiCommandArgument> arguments = this.subArgumentMap.get(newArgs);
            this.attemptToInsert(argumentToInsert, arguments);
            return this;
        }
    }

    protected void attemptToInsert(ApiCommandArgument argument, List<ApiCommandArgument> list) {
        if (list.contains(argument)) {
            Logger.warn("Attempting to register a duplicate SubCommand! Please review your code, im unable to give much information on this error" +
                    " if you are confused, please contact a developer");
            return;
        }

        list.add(argument);
    }

    /**
     * Sets the function to run when a {@link ConsoleCommandSender} sends a command
     * @param sender A {@link OnConsoleSender} lambda interface
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand onConsoleSender(OnConsoleSender sender) {
        this.onConsoleSender = sender;
        return this;
    }

    /**
     * Executes a TabCompleter for this command, only usage if you set a {@link OnPlayerSender} interface using {@link ApiCommand#onPlayerSender}
     * @param onComplete A {@link OnTabComplete} functional interface lambda
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand onTabComplete(OnTabComplete onComplete) {
        this.onTabComplete = onComplete;
        return this;
    }

    /**
     * Sets the command name when executing
     *
     * eg /{@param newName}
     *
     * @param newName A {@link String} without spaces
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand setCommandName(String newName) {
        if (!this.setName(newName)) {
            throw new RuntimeException("Attempting to change an already registered command name!");
        }
        return this;
    }

    /**
     * Sets the description of this command
     * @param newDesc Any {@link String}
     * @return instance of this ApiCommand
     * @since 2.1.5
     */
    public ApiCommand setCommandDescription(String newDesc) {
        this.setDescription(newDesc);
        return this;
    }

    /**
     * Sets the Command usage message for this command
     * @param usage {@link String} to set usage message to
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand setCommandUsage(String usage) {
        this.setUsage(usage);
        return this;
    }

    /**
     * Sets the aliases of this command
     * @param aliases Varargs of {@link String}'s, no strings with spaces
     * @return instance of this {@link ApiCommand}
     * @since 2.1.5
     */
    public ApiCommand setCommandAliases(String... aliases) {
        this.setAliases(Arrays.asList(aliases));
        return this;
    }

    /**
     * Sets the permission all users MUST have before being allowed to see sub commands/use sub commands
     * @param permission {@link String} any string in the format of "a.permission.use"
     * @return Instance of this class for chaining
     */
    public ApiCommand setBasePermission(String permission) {
        this.basePermission = permission;
        return this;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command,
                                      @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof final Player player) {
            if (this.onPlayerSender == null) return null; // Only allow player completer's
            if (this.onTabComplete == null) return null; // only allow non-null tab completer's
            return this.onTabComplete.onTabComplete(new PlayerTabComplete(player, new ArrayList<>(), Arrays.asList(args)));
        }

        return null;
    }
}
