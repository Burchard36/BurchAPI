package com.burchard36.api.command.actions;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

/**
 * executor method for {@link com.burchard36.api.command.interfaces.OnConsoleSender}
 */
public record ConsoleSendCommand(ConsoleCommandSender console, List<String> args) {
}
