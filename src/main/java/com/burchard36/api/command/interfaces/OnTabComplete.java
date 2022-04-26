package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.PlayerTabComplete;

import java.util.List;

/**
 * The functional interface when {@link org.bukkit.command.TabCompleter} is called for a {@link com.burchard36.api.command.ApiCommand}
 */
@FunctionalInterface
public interface OnTabComplete {
    /**
     * The method that {@link com.burchard36.api.command.ApiCommand} calls when using the tab completor
     * @param tabComplete A {@link PlayerTabComplete}
     * @return A {@link List} of {@link String}'s to be provided for tab completion
     */
    List<String> onTabComplete(final PlayerTabComplete tabComplete);
}
