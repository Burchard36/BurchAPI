package com.burchard36.command.actions;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public record ConsoleSendCommand(ConsoleCommandSender console, List<String> args) {
}
