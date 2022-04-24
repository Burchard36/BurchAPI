package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.PlayerSendCommand;

@FunctionalInterface
public interface OnPlayerSender {
    void onPlayerSender(PlayerSendCommand playerCommand);
}
