package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.ConsoleSendCommand;

@FunctionalInterface
public interface OnConsoleSender {
    void onConsoleSender(ConsoleSendCommand consoleCommand);
}
