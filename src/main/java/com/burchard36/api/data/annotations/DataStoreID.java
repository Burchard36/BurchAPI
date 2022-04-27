package com.burchard36.api.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the DataStore's name when loading the data store, use when implementing {@link com.burchard36.api.data.DataStore}
 * <br>
 * Using this interface also enabled the class for auto initialization
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataStoreID {

    /**
     * The name of the DataStore
     * @return {@link String}
     */
    String id() default "";
}
