package com.burchard36.api.data;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataStores {

    private final List<DataStore> dataStores;

    protected DataStores() {
        this.dataStores = new ArrayList<>();
    }

    /*
     * TODO: Find a way to optimize this, it can be rather taxing on larger projects, or not im not entirely sure
     */
    protected void injectDataStores() {
        final List<Class<?>> internalClasses = BurchAPI.INSTANCE.getPackageScanner()
                .subclassSearchQuery(DataStores.class.getPackage(), DataStore.class)
                .execute();
        final List<Class<?>> externalClasses = BurchAPI.INSTANCE.getPackageScanner()
                .subclassSearchQuery(BurchAPI.INSTANCE.getClass().getPackage(), DataStore.class)
                .execute();

        if (internalClasses.isEmpty()) {
            Logger.debug("Not loading any internal DataStore's, I assume you are disabling all the internal DataStore's?");
        }

    }

    public void registerDataStore(DataStore store) {

    }

}
