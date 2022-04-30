package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.PlayerSubArgument;

@FunctionalInterface
public interface OnPlayerSubArgument {
    void onArgument(PlayerSubArgument argument);
}
