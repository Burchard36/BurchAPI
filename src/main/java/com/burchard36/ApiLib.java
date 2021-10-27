package com.burchard36;

import com.burchard36.inventory.ClickableItem;
import com.burchard36.inventory.PluginInventory;
import com.burchard36.json.PluginDataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApiLib extends JavaPlugin implements Api {

    public JavaPlugin plugin;
    private PluginDataManager manager;
    public ApiLib lib;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onEnable() {
        this.lib = this.initializeApi(this);
    }

    public void onJoin(final PlayerJoinEvent e) {
        final ClickableItem item1 = new ClickableItem(Material.DIRT, 1)
                .onClick((onClick) -> {
                    Bukkit.getLogger().info("Clicker: " + onClick.getWhoClicked().getName());
                });
        new PluginInventory(9, "new name")
                .onClose(closeEvent -> {
                    String name = closeEvent.getPlayer().getName();
                    Bukkit.getLogger().info("Sup " + name);
                })
                .onOpen((openEvent) -> {
                    String name = openEvent.getPlayer().getName();
                    Bukkit.getLogger().info("Sup " + name);
                })
                .onClick((onClick) -> {
                    Bukkit.getLogger().info("You clicked slot: " + onClick.getSlot());
                })
                .addClickableItemAtSlot(0, item1)
                .register(this)
                .open(e.getPlayer());
    }

    /**
     * Initializes the API to a plugin
     */
    public ApiLib initializeApi(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.manager = new PluginDataManager(plugin);
        return this;
    }

    public PluginDataManager getDataManager() {
        return this.manager;
    }

    @Override
    public PluginDataManager getPluginDataManager() {
        return this.lib.getPluginDataManager();
    }
}
