package com.burchard36.command;

import com.burchard36.command.actions.ConsoleSendCommand;
import com.burchard36.command.actions.PlayerSendCommand;
import com.burchard36.command.interfaces.OnConsoleSender;
import com.burchard36.command.interfaces.OnPlayerSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ApiCommand extends Command {

    public OnPlayerSender onPlayerSender = null;
    public OnConsoleSender onConsoleSender = null;

    public ApiCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);

    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        final List<String> newArgs = Arrays.asList(args);

        if (sender instanceof final Player player) {
            //if (this.onPlayerSender == null) return false;
            this.onPlayerSender.onPlayerSender(new PlayerSendCommand(player, newArgs));
        } else {
            final ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
            //if (this.onConsoleSender == null) return false;
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
}
