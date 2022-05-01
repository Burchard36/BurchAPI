package com.burchard36.api.command.actions;

import com.burchard36.api.utils.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record SubArgument(CommandSender commandSender) {

    public boolean senderIsPlayer() {
        return commandSender instanceof Player;
    }

    /**
     * Gets a Player object from this SubArgument, only if the {@link CommandSender} is a {@link Player}
     * @return null if {@link SubArgument#commandSender()} is not a {@link Player}, otherwise returns a player object
     */
    public Player player() {
        if (!this.senderIsPlayer()) {
            Logger.warn("Attempting to call a Player cast to a" +
                    " CommandSender for a SubArgument on a command! Review your code, I cant give you too much information on this, contact a developer if you are lost!");
            return null;
        }
        return (Player) commandSender;
    }

}
