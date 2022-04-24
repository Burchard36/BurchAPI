package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.PlayerTabComplete;

import java.util.List;

@FunctionalInterface
public interface OnTabComplete {
    List<String> onTabComplete(final PlayerTabComplete tabComplete);
}
