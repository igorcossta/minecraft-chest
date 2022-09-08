package io.github.igorcossta.utils;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemWrapper {
    private Map<Integer, ItemStack> items;

    public ItemWrapper(Map<Integer, ItemStack> items) {
        this.items = items;
    }

    public ItemWrapper() {
        this.items = new HashMap<>();
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }
}
