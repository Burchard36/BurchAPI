package com.burchard36.api.data.json;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.PluginJsonWriter;
import com.burchard36.api.data.FileDataStore;
import com.burchard36.api.data.annotations.DataStoreID;
import com.burchard36.api.data.annotations.PlayerDataFile;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.utils.reflections.PackageScanner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The default data store for PlayerData provided by BurchAPI
 */
@DataStoreID(id = "internal_playerJsonDataStore")
public class PlayerJsonDataStore implements FileDataStore, Listener {

    protected final BurchAPI api;
    protected final PluginJsonWriter writer;
    protected BukkitTask autoSaveTask;
    protected final HashMap<UUID, JsonPlayerDataFile> cache;
    protected Class<? extends JsonPlayerDataFile> dataClass;

    public PlayerJsonDataStore(BurchAPI api) {
        this.api = api;
        this.writer = this.api.getJsonWriter();
        this.cache = new HashMap<>();
    }

    @Override
    public void onEnable() {
        Logger.log("Initializing PlayerJsonDataStore!");

        /* Search for classes extending JsonPlayerDataFile */
        final PackageScanner<JsonPlayerDataFile> scanner = this.api.getPackageScanner();
        this.api.getPackageScanner()
                .subclassSearchQuery(this.api.getClass().getPackage(), JsonPlayerDataFile.class)
                .execute();

        /* Find classes with the annotation PlayerDataFile */
        final HashMap<Class<? extends Annotation>, Class<? extends JsonPlayerDataFile>>
                annotatedClasses = scanner.findWithClassConstructorAnnotations(PlayerDataFile.class);

        if (annotatedClasses.size() > 1) {
            Logger.error("Found multiple classes extending JsonPlayerDataFile with a @PlayerDataFile marked Annotation!" +
                    "Please review our documentation, you are only allowed to have one class annotated with @PlayerDataFile");
        }

        annotatedClasses.forEach((keyAnnotation, keyClass) -> {

        });


    }

    @Override
    public void onReload() {
        Logger.log("Reloading PlayerJsonDataStore");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public List<Class<? extends JsonDataFile>> dataFiles() {
        return List.of();
    }

    @Override
    public BukkitTask autoSaveTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimerAsynchronously(this.api, 0, (20 * 60)
                * this.api.getApiSettings().getDataStoreSettings().getPlayerDataAutoSave());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void asyncPreLogin(AsyncPlayerPreLoginEvent event) {
        final UUID playerUUID = event.getUniqueId();
    }
}
