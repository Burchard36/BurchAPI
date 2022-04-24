package com.burchard36.api.command.actions;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public record ConsoleSendCommand(ConsoleCommandSender console, List<String> args) {
}
