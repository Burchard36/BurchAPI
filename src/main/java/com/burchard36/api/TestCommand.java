package com.burchard36.api;

import com.burchard36.api.command.actions.PlayerTabComplete;
import com.burchard36.api.command.annotation.RegisterCommand;
import com.burchard36.api.command.ApiCommand;

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
