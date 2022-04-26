package com.burchard36.api.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface to set a commands description for a class extending  {@link com.burchard36.api.command.ApiCommand}
 *
 * This annotation is optional, however it will override the command description's
 * {@link String} from a {@link RegisterCommand} Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandDescription {
    /**
     * Description of what the {@link com.burchard36.api.command.ApiCommand} does
     * @return String of what the commands description will be
     */
    String description() default "";
}
