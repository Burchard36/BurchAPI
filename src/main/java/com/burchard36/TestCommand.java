package com.burchard36;

import com.burchard36.command.ApiCommand;
import com.burchard36.command.actions.PlayerTabComplete;
import com.burchard36.command.annotation.RegisterCommand;

@RegisterCommand(name = "newCommand",
description = "Heyo special command lool",
usageMessage = "/commanddumbass",
aliases = {"dumbassone", "dumbasstwo"})
public class TestCommand extends ApiCommand {
    public TestCommand() {
        this.onPlayerSender((playerSent) -> {

        })
        .onTabComplete(PlayerTabComplete::tabCompleteOptions);
    }
}
