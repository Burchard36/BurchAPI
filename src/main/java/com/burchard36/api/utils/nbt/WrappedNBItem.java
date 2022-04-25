package com.burchard36.api.utils.nbt;

import com.burchard36.api.utils.ItemWrapper;
import org.bukkit.inventory.ItemStack;

public class WrappedNBItem {

    private final ItemStack itemStack;

    public WrappedNBItem(final ItemWrapper wrapper) {
        this.itemStack = wrapper.getItemStack();
    }

}
