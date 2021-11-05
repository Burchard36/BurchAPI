package com.burchard36.command.actions;

import org.bukkit.entity.Player;

import java.util.List;

public record PlayerSendCommand(Player player, List<String> args) {

    public List<String> getArguments() {
        return this.args;
    }

    public Player getSendingPlayer() {
        return this.player;
    }
}
