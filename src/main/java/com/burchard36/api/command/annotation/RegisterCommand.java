package com.burchard36.api.command.annotation;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets specific command settings for a class extending {@link com.burchard36.api.command.ApiCommand}
 *
 * This is required, unless you prefer to use {@link CommandName} {@link CommandUsage} {@link CommandDescription} {@link CommandAliases}
 * instead
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterCommand {

    /**
     * Name to set for the class extending {@link com.burchard36.api.command.ApiCommand}
     *
     * This field is absolutely required when using this annotation, unless your are using a {@link CommandName}
     * annotation
     *
     * @return Nonnull String, and Not empty String
     */
    @Nonnull String name() default "";

    /**
     * Sets the description for the class extending {@link com.burchard36.api.command.ApiCommand}
     *
     * This field is optional, however if a {@link CommandDescription} Annotation is set, that annotation will override this value
     *
     * @return Any Nonnull string
     */
    @Nonnull String description() default "";

    /**
     * Sets the usage method of a class extending {@link com.burchard36.api.command.ApiCommand}
     *
     * This field is optional, however if a {@link CommandUsage} Annotation is set, that annotation will override this value
     *
     * @return Any Nonnull String
     */
    @Nonnull String usageMessage() default "";

    /**
     * Sets the Aliases of a class extending {@link com.burchard36.api.command.ApiCommand}
     *
     * This field is optional, however if a {@link CommandAliases} Annotation is set, that annotation will override this value
     *
     * @return Nonnull {@link java.util.List} of {@link String}'s that have no spaces in them
     */
    @Nonnull String[] aliases() default {};

    /**
     * Sets the required permission that a {@link org.bukkit.entity.Player} must have before executing
     * said command
     *
     * This annotation may also be used on both {@link com.burchard36.api.command.ApiCommand} AND {@link com.burchard36.api.command.ApiCommandArgument}
     *
     * This field is optional, however if a {@link RequiredPermission} Annotation is set, that annotation will override this value
     * @return Nonnull {@link String}, this is the permission, eg "my.permission.do.this"
     */
    @Nonnull String requiredPermission() default "";
}
