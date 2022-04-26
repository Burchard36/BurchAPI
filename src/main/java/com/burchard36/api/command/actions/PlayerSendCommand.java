package com.burchard36.api.command.actions;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Executor method for {@link com.burchard36.api.command.interfaces.OnPlayerSender}
 */
public record PlayerSendCommand(Player player, List<String> args) {
}
