package com.burchard36.api.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the CommandName for a class extending {@link com.burchard36.api.command.ApiCommand}
 * This also queue's the class for being AutoRegistered on the program startup
 * if your class extending {@link com.burchard36.api.command.ApiCommand} does not have this annotation,
 * it needs to have a {@link RegisterCommand} annotation instead
 *
 * This is required, unless you are using a {@link RegisterCommand} Annotation instead,
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandName {
    /**
     * Name of the command for execution in a class extending {@link com.burchard36.api.command.ApiCommand}
     * @return A {@link String} with no spaces and uppercase letters (Uppercase letters get automatically set to all lowercase)
     */
    String name() default "";
}
