package com.burchard36.api.command.actions;

import com.burchard36.api.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public record SubArgument(CommandSender commandSender, List<String> args) {

    public boolean senderIsPlayer() {
        return commandSender instanceof Player;
    }

    public Optional<OfflinePlayer> playerPresentAt(int index) {
        if (Bukkit.getOnlinePlayers().stream().anyMatch(p -> p.getName().equals(args.get(index))))
            return Optional.of(Bukkit.getOfflinePlayer(Bukkit.getPlayer(args.get(index)).getUniqueId()));
        if (!Bukkit.getOfflinePlayer(args.get(index)).hasPlayedBefore()) return Optional.empty();
        return Optional.of(Bukkit.getOfflinePlayer(args.get(index)));
    }

    public Optional<Material> materialPresentAt(int index) {
        final Material mat = Material.getMaterial(args.get(index));
        if (mat == null) return Optional.empty();
        return Optional.of(mat);
    }

    public Optional<Integer> integerPresentAt(int index) {
        final int integer;
        try {
            integer = Integer.parseInt(args.get(index));
            return Optional.of(integer);
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
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
