package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The settings for the API to use.
 *
 * No not initialize this, {@link BurchAPI} does this already if your main class extends it.
 *
 * @author Dalton Burchard
 * @since 2.1.5
 */
@Getter
class ApiSettings {

    private boolean useCommandModule;
    private boolean useInventoryModule;
    private final List<Class<? extends ApiCommand>> commandAutoRegisterBlacklist;

    /**
     * Creates a new instance of this class
     *
     * You should not be initializing this, the API does this already
     * @since 2.1.5
     */
    public ApiSettings() {
        this.useCommandModule = true;
        this.useInventoryModule = true;
        this.commandAutoRegisterBlacklist = new ArrayList<>();
    }

    /**
     * Blocks a specific command class from being automatically registered
     * @param clazz Class file instance of your class extending {@link ApiCommand}
     * @return Instance of this {@link ApiSettings}
     * @since 2.1.5
     */
    public ApiSettings blockCommandFromLoading(Class<? extends ApiCommand> clazz) {
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
