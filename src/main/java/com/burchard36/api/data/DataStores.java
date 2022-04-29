package com.burchard36.api.data;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.data.annotations.DataStoreID;
import com.burchard36.api.utils.Logger;
import com.burchard36.api.utils.reflections.PackageScanner;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.lang.annotation.Annotation;
import java.util.*;

public class DataStores {

    protected final PackageScanner<DataStore> scanner;
    protected final HashMap<String, DataStore> dataStores;
    protected final HashMap<FileDataStore, BukkitTask> fileDataStoreAutoSaveTasks;

    public DataStores() {
        this.scanner = BurchAPI.INSTANCE.newPackageScanner();
        this.dataStores = new HashMap<>();
        this.fileDataStoreAutoSaveTasks = new HashMap<>();
        this.injectDataStores();
    }

    public void disableAllDataStores() {
        this.dataStores.values().forEach(DataStore::onDisable);
        this.fileDataStoreAutoSaveTasks.values().forEach(BukkitTask::cancel);

        this.dataStores.clear();
        this.fileDataStoreAutoSaveTasks.clear();
    }

    /*
     * TODO: Find a way to optimize this, it can be rather taxing on larger projects, or not im not entirely sure
     */
    protected void injectDataStores() {
        this.scanner.subclassSearchQuery(DataStores.class.getPackage(), DataStore.class).execute();
        final HashMap<Class<? extends Annotation>, Class<? extends DataStore>> internalAnnotatedClasses =
                this.scanner.findWithClassConstructorAnnotations(DataStoreID.class);
        this.scanner.subclassSearchQuery(BurchAPI.INSTANCE.getClass().getPackage(), DataStore.class);
        final HashMap<Class<? extends Annotation>, Class<? extends DataStore>> externalAnnotatedClasses =
                this.scanner.findWithClassConstructorAnnotations(DataStoreID.class);

        if (internalAnnotatedClasses.isEmpty()) Logger.debug("Not loading any internal DataStore's, I assume you are disabling all the internal DataStore's?");

        if (externalAnnotatedClasses.isEmpty()) Logger.debug("Not loading any external DataStore's. I assume you didn't create any yet, that's fine!");

        List<Class<? extends DataStore>> classes = new ArrayList<>();
        classes.addAll(internalAnnotatedClasses.values());
        classes.addAll(externalAnnotatedClasses.values());

        if (classes.isEmpty()) {
            Logger.warn("No DataStore's were found when enabling the DataStore API! Why not disable this setting if your not using any DataStore's?");
            return;
        }

        classes.forEach((clazz) -> {
            final PackageScanner.InvocationResult<DataStore, PackageScanner.InvocationErrorStatus> result =
                    this.scanner.invokeClass(clazz);

            if (!result.wasSuccessfullyInvoked()) {
                Logger.warn("Exception when invoking DataStore: " + clazz.getName() + " err: " + result.getValue().name());
                switch (result.getValue()) {
                    // TODO: Implement error handling
                }
            } else {
                Logger.log("Attempting to Register DataStore: " + result.getKey().getClass().getName());
                this.registerDataStore(result.getKey());
            }
        });
    }

    /**
     * Registers a {@link DataStore} implementing class that you have, can be used if you don't want to use
     * to autoload classes annotated with {@link DataStoreID} (Though, the class still needs to be annotated with this), disable loading in {@link com.burchard36.api.ApiSettings}
     * @param store a class implementing {@link DataStore}
     */
    public void registerDataStore(DataStore store) {
        final DataStoreID storeID = store.getClass().getAnnotation(DataStoreID.class);

        if (Listener.class.isAssignableFrom(store.getClass())) {
            Logger.debug("DataStore was assignable from Listener, initializing Listener automatically");
            BurchAPI.INSTANCE.getServer().getPluginManager().registerEvents((Listener) store, BurchAPI.INSTANCE);
        }

        Logger.log("Initializing datastore with ID: " + storeID.id());
        store.onEnable(); // Enable the DataStore

        this.dataStores.putIfAbsent(storeID.id(), store);
    }

}
