package com.burchard36.api.data.annotations;

/**
 * Sets the DataStore's name when loading the data store, use when implementing {@link com.burchard36.api.data.DataStore}
 */
public @interface DataStoreName {

    /**
     * The name of the DataStore
     * @return {@link String}
     */
    String name() default "";
}
