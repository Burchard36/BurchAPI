package com.burchard36.api.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the command usage message for a class extending {@link com.burchard36.api.command.ApiCommand}
 *
 * This is optional, and will override command usage {@link String}'s from a {@link RegisterCommand} Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandUsage {
    /**
     * Usage message of the class extending {@link com.burchard36.api.command.ApiCommand}
     * @return {@link String} of what the {@link com.burchard36.api.command.ApiCommand}'s message should be
     */
    String usageMessage() default "";
}
