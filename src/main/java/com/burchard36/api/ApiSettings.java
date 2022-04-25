package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiSettings {

    private boolean useCommandModule;
    private boolean useInventoryModule;
    private final List<Class<? extends ApiCommand>> commandAutoRegisterBlacklist;

    public ApiSettings() {
        this.useCommandModule = true;
        this.useInventoryModule = true;
        this.commandAutoRegisterBlacklist = new ArrayList<>();
    }

    public ApiSettings blockCommandFromLoading(Class<? extends ApiCommand> clazz) {
        this.commandAutoRegisterBlacklist.add(clazz);
        return this;
    }

    public ApiSettings useCommandModule(boolean useCommandModule) {
        this.useCommandModule = useCommandModule;
        return this;
    }

    public ApiSettings useInventoryModule(boolean useInventoryModule) {
        this.useInventoryModule = useInventoryModule;
        return this;
    }

}
