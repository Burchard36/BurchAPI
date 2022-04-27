package com.burchard36.api.data;

public enum InternalDataStore {
    JSON_DATA_STORE("internal_jsonDataStore"),
    JSON_PLAYER_DATA_STORE("internal_playerJsonDataStore");

    final String enumName;

    InternalDataStore(String name) {
        this.enumName = name;
    }

    public String getEnumName() {
        return this.enumName;
    }

    public InternalDataStore parse(String suspected) {
        for (InternalDataStore store : InternalDataStore.values()) {
            if (store.getEnumName().equalsIgnoreCase(suspected)) return store;
        }
        return null;
    }
}
