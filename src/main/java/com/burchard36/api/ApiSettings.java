package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import com.burchard36.api.data.DataStore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The settings for the API to use.
 *
 * do not initialize this, {@link BurchAPI} does this already if your main class extends it.
 *
 * @author Dalton Burchard
 * @since 2.1.5
 */
@Getter
public class ApiSettings {

    private boolean useCommandModule;
    private boolean useInventoryModule;
    private final List<Class<? extends ApiCommand>> commandAutoRegisterBlacklist;
    protected final List<Class<? extends DataStore>> dataStoreAutoRegisterBlacklist;

    /**
     * Creates a new instance of this class
     * @since 2.1.5
     */
    protected ApiSettings() {
        this.useCommandModule = true;
        this.useInventoryModule = true;
        this.commandAutoRegisterBlacklist = new ArrayList<>();
        this.dataStoreAutoRegisterBlacklist = new ArrayList<>();
    }

    /**
     * Blocks a DataStore storage handler not load on STARTUP
     * @param clazz A class that extends {@link DataStore} typically in YourClass.class format
     * @return instance of this class
     * @since 2.1.8
     */
    public final ApiSettings blockDataStoreFromLoading(Class<? extends DataStore> clazz) {
        if (this.dataStoreAutoRegisterBlacklist.contains(clazz)) return this;
        this.dataStoreAutoRegisterBlacklist.add(clazz);
        return this;
    }

    /**
     * Blocks a specific command class from being automatically registered
     * @param clazz Class file instance of your class extending {@link ApiCommand}
     * @return Instance of this {@link ApiSettings}
     * @since 2.1.5
     */
    public ApiSettings blockCommandFromLoading(Class<? extends ApiCommand> clazz) {
        if (this.commandAutoRegisterBlacklist.contains(clazz)) return this;
        this.commandAutoRegisterBlacklist.add(clazz);
        return this;
    }

    /**
     * Sets whether the Command module should automatically register all of its commands
     *
     * This default value is true
     *
     * @param useCommandModule A {@link Boolean}, false if you do not want commands to automtically register
     * @return Instance of this {@link ApiSettings}
     * @since 2.1.5
     */
    public ApiSettings useCommandModule(boolean useCommandModule) {
        this.useCommandModule = useCommandModule;
        return this;
    }

    /**
     * Sets whether the Listener for the Inventory API should be enabled
     *
     * This value defaults to true
     *
     * @param useInventoryModule A {@link Boolean}, false if the Listener should not be enabled
     * @return Instance of this {@link ApiSettings}
     * @since 2.1.5
     */
    public ApiSettings useInventoryModule(boolean useInventoryModule) {
        this.useInventoryModule = useInventoryModule;
        return this;
    }
}
