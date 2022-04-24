package com.burchard36.api.command.actions;

import org.bukkit.entity.Player;

import java.util.List;

public record PlayerSendCommand(Player player, List<String> args) {
}
