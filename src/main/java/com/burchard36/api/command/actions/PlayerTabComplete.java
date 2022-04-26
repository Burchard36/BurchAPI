package com.burchard36.api.command.actions;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Executor method for {@link com.burchard36.api.command.interfaces.OnTabComplete}
 */
public record PlayerTabComplete(Player player, List<String> tabCompleteOptions, List<String> aliases) {
}
