package com.burchard36.api.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface to set a list of Aliases for a class extending {@link com.burchard36.api.command.ApiCommand}
 *
 * This is optional, however this will override the aliases {@link String[]} set in a {@link RegisterCommand} annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAliases {
    /**
     * List of aliases that the class extending {@link com.burchard36.api.command.ApiCommand} can also use for execution
     * @return {@link String[]} Each containing {@link String} must have no spaces and must be lowercase (Uppercase letters are automtically
     * switched back to lowercase)
     */
    String[] aliases() default {};
}
