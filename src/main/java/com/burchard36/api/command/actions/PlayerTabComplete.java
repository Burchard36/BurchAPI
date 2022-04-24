package com.burchard36.api.command.actions;

import org.bukkit.entity.Player;

import java.util.List;

public record PlayerTabComplete(Player player, List<String> tabCompleteOptions, List<String> aliases) {
}
