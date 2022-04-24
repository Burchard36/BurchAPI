package com.burchard36.api.command;

import com.burchard36.api.command.actions.ConsoleSendCommand;
import com.burchard36.api.command.actions.PlayerSendCommand;
import com.burchard36.api.command.actions.PlayerTabComplete;
import com.burchard36.api.command.interfaces.OnConsoleSender;
import com.burchard36.api.command.interfaces.OnPlayerSender;
import com.burchard36.api.command.interfaces.OnTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiCommand extends Command implements TabCompleter {

    public OnPlayerSender onPlayerSender = null;
    public OnConsoleSender onConsoleSender = null;
    public OnTabComplete onTabComplete = null;

    /* We check this when registering a command, if the command name is set like this, then the end user did not use setCommandName() on and empty constructor */
    private static final String DO_NOT_REGISTER = "BurchAPIPlaceholderDoNoteRegister36";

    public ApiCommand() {
        super(DO_NOT_REGISTER);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender instanceof final Player player) {
            if (this.onPlayerSender == null) return null; // Only allow player completer's
            if (this.onTabComplete == null) return null; // only allow non-null tab completer's
            return this.onTabComplete.onTabComplete(new PlayerTabComplete(player, new ArrayList<>(), Arrays.asList(args)));
        }

        return null;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
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

    public ApiCommand onPlayerSender(OnPlayerSender sender) {
        this.onPlayerSender = sender;
        return this;
    }

    public ApiCommand onConsoleSender(OnConsoleSender sender) {
        this.onConsoleSender = sender;
        return this;
    }

    public ApiCommand onTabComplete(OnTabComplete onComplete) {
        this.onTabComplete = onComplete;
        return this;
    }

    public ApiCommand setCommandName(String newName) {
        if (!this.setName(newName)) {
            throw new RuntimeException("Attempting to change an already registered command name!");
        }
        return this;
    }

    public ApiCommand setCommandDescription(String newDesc) {
        this.setDescription(newDesc);
        return this;
    }

    public ApiCommand setCommandUsage(String usage) {
        this.setUsage(usage);
        return this;
    }

    public ApiCommand setCommandAliases(String... aliases) {
        this.setAliases(Arrays.asList(aliases));
        return this;
    }
}
