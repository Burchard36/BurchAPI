package com.burchard36.command;

import com.burchard36.command.actions.PlayerSendCommand;
import com.burchard36.command.interfaces.OnPlayerSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiCommand extends BukkitCommand {

    public OnPlayerSender onPlayerSender = null;

    public ApiCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        final List<String> newArgs = Arrays.asList(args);

        if (sender instanceof final Player player) {
            this.onPlayerSender.onPlayerSender(new PlayerSendCommand(player, newArgs));
        }

        return false;
    }

    public ApiCommand onPlayerSender(OnPlayerSender sender) {
        this.onPlayerSender = sender;
        return this;
    }


}
