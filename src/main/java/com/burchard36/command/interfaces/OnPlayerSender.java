package com.burchard36.command.interfaces;

import com.burchard36.command.actions.PlayerSendCommand;

@FunctionalInterface
public interface OnPlayerSender {
    void onPlayerSender(PlayerSendCommand playerCommand);
}
