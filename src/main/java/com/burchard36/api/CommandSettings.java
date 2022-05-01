package com.burchard36.api;

import lombok.Getter;

@Getter
public class CommandSettings {

    private boolean useStrictSubArgumentChecking;
    private final ApiSettings settings;

    protected CommandSettings(final ApiSettings settings) {
        this.useStrictSubArgumentChecking = true;
        this.settings = settings;
    }

    /**
     * Decides whether you want an equals() or equalsIgnoreCase() call for checking your sub-arguments
     * <br>
     * EG: When true, and a player enters "hElp", but your sub-argument is set to "help", the sub argument will NOT fire
     * @param value A {@link Boolean}
     * @return Instance of this class for chaining
     * @since 2.1.8
     */
    public CommandSettings setStrictSubArgumentChecking(boolean value) {
        this.useStrictSubArgumentChecking = value;
        return this;
    }

    /**
     * Joins back to {@link ApiSettings} for easier chaining.
     * @return Instance of {@link ApiSettings} for chaining
     * @since 2.1.8
     */
    public ApiSettings join() {
        return this.settings;
    }

}
