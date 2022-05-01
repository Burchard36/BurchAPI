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

    protected boolean papiSupport;
    protected boolean vaultPermissionSupport;
    protected boolean useCommandModule;
    protected boolean useInventoryModule;
    protected final List<Class<? extends ApiCommand>> commandAutoRegisterBlacklist;
    protected final List<Class<? extends DataStore>> dataStoreAutoRegisterBlacklist;

    private final DataStoreSettings dataStoreSettings;
    private final CommandSettings commandSettings;

    protected ApiSettings() {
        this.papiSupport = true;
        this.vaultPermissionSupport = true;
        this.useCommandModule = true;
        this.useInventoryModule = true;
        this.commandAutoRegisterBlacklist = new ArrayList<>();
        this.dataStoreAutoRegisterBlacklist = new ArrayList<>();
        this.dataStoreSettings = new DataStoreSettings(this);
        this.commandSettings = new CommandSettings(this);
    }

    /**
     * Sets whether you want Vault Permission checking support
     * <br>
     * When enabled, the plugin will attempt to check permissions with vault,
     *
     * @param value {@link Boolean} true if you want support for this (defaults to true) false if not
     * @return Instance of this class for chaining
     * @since 2.1.8
     */
    public final ApiSettings setVaultPermissionSupport(boolean value) {
        this.vaultPermissionSupport = value;
        return this;
    }

    /**
     * Sets whether you want PlaceholderAPI support (true) or want none (false)
     * @param value A {@link Boolean}
     * @return Instance of this class for chaining
     * @since 2.1.8
     */
    public final ApiSettings setPlaceholderAPISupport(boolean value) {
        this.papiSupport = value;
        return this;
    }

    /**
     * Blocks a DataStore storage handler not load on STARTUP
     * @param clazz A class that extends {@link DataStore} typically in YourClass.class format
     * @return Instance of this class for chaining
     * @since 2.1.8
     */
    public final ApiSettings blockDataStoreFromLoading(Class<? extends DataStore> clazz) {
        if (this.dataStoreAutoRegisterBlacklist.contains(clazz)) return this;
        this.dataStoreAutoRegisterBlacklist.add(clazz);
        return this;
    }

    /**
     * Blocks a specific command class from being automatically registered
     * @param classes Varargs of Class file instances of your class extending {@link ApiCommand}
     * @return Instance of this class for chaining
     * @since 2.1.5
     */
    @SafeVarargs
    public final ApiSettings blockCommandsFromLoading(Class<? extends ApiCommand>... classes) {
        for (Class<? extends ApiCommand> clazz : classes) {
            if (this.commandAutoRegisterBlacklist.contains(clazz)) return this;
            this.commandAutoRegisterBlacklist.add(clazz);
        }
        return this;
    }

    /**
     * Sets whether the Command module should automatically register all of its commands
     *
     * This default value is true
     *
     * @param useCommandModule A {@link Boolean}, false if you do not want commands to automatically register
     * @return Instance of this class for chaining
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
     * @return Instance of this class for chaining
     * @since 2.1.5
     */
    public ApiSettings useInventoryModule(boolean useInventoryModule) {
        this.useInventoryModule = useInventoryModule;
        return this;
    }
}
