package com.burchard36.api.data.annotations;

/**
 * Sets the DataStore's name when loading the data store, use when implementing {@link com.burchard36.api.data.DataStore}
 * <br>
 * Using this interface also enabled the class for auto initialization
 */
public @interface DataStoreID {

    /**
     * The name of the DataStore
     * @return {@link String}
     */
    String id() default "";
}
