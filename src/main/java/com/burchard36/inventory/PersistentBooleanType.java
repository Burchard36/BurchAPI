package com.burchard36.inventory;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PersistentBooleanType implements PersistentDataType<Boolean, Boolean> {

    @Override
    public @NotNull Class<Boolean> getPrimitiveType() {
        return boolean.class;
    }

    @Override
    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public @NotNull Boolean toPrimitive(@NotNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        return complex;
    }

    @Override
    public @NotNull Boolean fromPrimitive(@NotNull Boolean primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive;
    }
}
