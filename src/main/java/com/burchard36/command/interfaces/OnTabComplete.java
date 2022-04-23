package com.burchard36.command.interfaces;

import com.burchard36.command.actions.PlayerTabComplete;

import java.util.List;

@FunctionalInterface
public interface OnTabComplete {
    List<String> onTabComplete(final PlayerTabComplete tabComplete);
}
