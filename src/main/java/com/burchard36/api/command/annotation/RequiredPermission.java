package com.burchard36.api.command.annotation;

public @interface RequiredPermission {

    /**
     * Sets the required permission that a {@link org.bukkit.entity.Player} must have before executing
     * said command
     *
     * This annotation may also be used on both {@link com.burchard36.api.command.ApiCommand} AND {@link com.burchard36.api.command.ApiCommandArgument}
     *
     * This field is optional an overrides values set in {@link RegisterCommand}
     * @return Nonnull {@link String}, this is the permission, eg "my.permission.do.this"
     */
    String requiredPermission() default "";
}
