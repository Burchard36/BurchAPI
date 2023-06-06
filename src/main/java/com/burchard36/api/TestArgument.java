package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import com.burchard36.api.command.actions.SubArgument;
import com.burchard36.api.command.annotation.CommandName;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandName(name = "help")
public class TestArgument extends ApiCommand {

    public static void onArgument(SubArgument arg) {

    }

   public TestArgument() {

       this.subArgument("testtt", null, TestArgument::onArgument)
               .subArgument("test", null, (subArgument) -> {
                   if (!subArgument.senderIsPlayer()) return;

               })
               .subArgument("test %player%", "needs.me", (subArgument) -> {
                    if (subArgument.playerPresentAt(1).isEmpty()) return;// send msg, maybe have boolean return
                    final OfflinePlayer player = subArgument.playerPresentAt(1).get();
                    subArgument.commandSender().sendMessage("You sent me: " + player.getName());
               })
               .subArgument("give %player% %item% %amount%", "needs.give", (subArgument) -> {
                    if (subArgument.playerPresentAt(1).isEmpty()) return; // send msg, maybe have boolean return
                    if (subArgument.integerPresentAt(2).isEmpty()) return; // send msg, maybe have boolean return
                    if (subArgument.materialPresentAt(3).isEmpty()) return; // send msg, maybe have boolean return

                    final OfflinePlayer offlinePlayer = subArgument.playerPresentAt(1).get();
                    if (offlinePlayer.isOnline()) {
                        final Player player = ((Player) offlinePlayer);
                        final Material mat = subArgument.materialPresentAt(3).get();
                        final int amt = subArgument.integerPresentAt(2).get();
                        player.getInventory().addItem(new ItemStack(mat, amt));
                    }
               })
               .onPlayerSender((playerSent) -> playerSent.player().sendMessage("Dumbass you need to send arguments"))
               .onConsoleSender((consoleSent) -> consoleSent.console().sendMessage("Your even more stupid, you sent it through console!"));
   }
}
