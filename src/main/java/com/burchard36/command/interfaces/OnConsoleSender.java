package com.burchard36.command.interfaces;

import com.burchard36.command.actions.ConsoleSendCommand;

@FunctionalInterface
public interface OnConsoleSender {
    void onConsoleSender(ConsoleSendCommand consoleCommand);
}
