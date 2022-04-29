package com.burchard36.api.data.json;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.data.json.writer.PluginJsonWriter;
import com.burchard36.api.data.FileDataStore;
import com.burchard36.api.data.annotations.DataStoreID;
import com.burchard36.api.data.annotations.PlayerDataFile;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.utils.reflections.PackageScanner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.UUID;

/**
 * The default data store for PlayerData provided by BurchAPI
 */
@DataStoreID(id = "internal_playerJsonDataStore")
public class PlayerJsonDataStore implements FileDataStore, Listener {

    protected final BurchAPI api;
    protected final PluginJsonWriter writer;
    protected BukkitTask autoSaveTask;
    protected final HashMap<UUID, JsonDataFile> cache;
    protected Class<? extends JsonPlayerDataFile> dataClass = null;
    protected PackageScanner<JsonPlayerDataFile> scanner;

    public PlayerJsonDataStore(BurchAPI api) {
        this.api = api;
        this.writer = this.api.getJsonWriter();
        this.cache = new HashMap<>();
        this.scanner = new PackageScanner<>();
    }

    @Override
    public void onEnable() {
        Logger.log("Initializing PlayerJsonDataStore!");

        /* Search for classes extending JsonPlayerDataFile */
        /* If you are looking at this as an example, skip everything im doing for now until i tell you not to */
        this.scanner.subclassSearchQuery(this.api.getClass().getPackage(), JsonPlayerDataFile.class)
                .execute();

        /* Find classes with the annotation PlayerDataFile */
        final HashMap<Class<? extends Annotation>, Class<? extends JsonPlayerDataFile>>
                annotatedClasses = this.scanner.findWithClassConstructorAnnotations(PlayerDataFile.class);

        if (annotatedClasses.size() > 1) {
            Logger.error("Found multiple classes extending JsonPlayerDataFile with a @PlayerDataFile marked Annotation!" +
                    "Please review our documentation, you are only allowed to have one class annotated with @PlayerDataFile");
        }

        annotatedClasses.forEach((keyAnnotation, keyClass) -> {
            if (keyAnnotation == PlayerDataFile.class) {
                /* Try to invoke the Annotated class constructor using your invocation methods */
                final PackageScanner.InvocationResult<JsonPlayerDataFile, PackageScanner.InvocationErrorStatus>
                        invocationResult = scanner.invokeClass(keyClass);
                if (!invocationResult.wasSuccessfullyInvoked()) {
                    switch (invocationResult.getValue()) {
                        case ERR_INVOCATION -> {
                            // lets ignore the error for now and just debug log it
                            Logger.warn("ERR_INVOCATION result from auto-loading " + keyClass.getName() + " class!");
                        }
                        case ERR_INVALID_CONSTRUCTOR -> {
                            // same thing as previous error
                            Logger.warn("ERR_INVALID_CONSTRUCTOR result from auto-loading" + keyClass.getName() + " class!");
                        }
                    }
                } else if (this.dataClass == null){
                    final JsonPlayerDataFile dataFile = invocationResult.getKey();
                    Logger.log("Successfully found a needed data class! Invoked class: " + dataFile.getClass().getName());
                    this.dataClass = dataFile.getClass();
                } else Logger.error("Another class with @PlayerDataFile annotation existed! It will not be loaded! Class name: " + keyClass.getName());
            }
        });


        //this.api.getServer().getPluginManager().registerEvents(this, this.api);
        Logger.log("Finished initializing PlayerJsonDataStore!");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public BukkitTask autoSaveTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Logger.log("Attempting to auto-save data for PlayerJsonDataStore...");
            }
        }.runTaskTimerAsynchronously(this.api, 0, (20 * 60)
                * this.api.getApiSettings().getDataStoreSettings().getPlayerDataAutoSave());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void asyncPreLogin(AsyncPlayerPreLoginEvent event) {
        final UUID playerUUID = event.getUniqueId();
        final JsonPlayerDataFile newFile = this.invokeNewDataFile();
        newFile.provideUUID(playerUUID);
        this.writer.createFile(newFile).thenAccept((optionalDataFile) ->
                optionalDataFile.ifPresent(jsonDataFile ->
                        PlayerJsonDataStore.this.cache.putIfAbsent(playerUUID, jsonDataFile)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerQuitEvent event) {
        final UUID playerUUID = event.getPlayer().getUniqueId();
        if (this.cache.containsKey(playerUUID)) {
            this.writer.writeDataToFile(this.cache.get(playerUUID)).thenAcceptAsync((playerData) -> {
                Logger.log("Successfully saved data for player: " + playerUUID);
            });
        }
    }

    /* If you are looking at this file as an example Datatore, ignore this method */
    protected final JsonPlayerDataFile invokeNewDataFile() {
        final PackageScanner.InvocationResult<JsonPlayerDataFile, PackageScanner.InvocationErrorStatus>
            invocationResult = this.scanner.invokeClass(this.dataClass);

        if (!invocationResult.wasSuccessfullyInvoked()) {
            throw new PlayerJsonInvocationException("Error invoking JsonPlayerDataFile! If you believe this to be an error please open an issue on our github.");
        } else return invocationResult.getKey();
    }

    /**
     * Thrown when the result of invoking a new file fails.
     */
    public static class PlayerJsonInvocationException extends RuntimeException {
        protected PlayerJsonInvocationException(String msg) {
            super(msg);
        }
    }
}
