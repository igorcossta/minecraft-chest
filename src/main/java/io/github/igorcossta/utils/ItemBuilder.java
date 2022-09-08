package io.github.igorcossta.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public final class ItemBuilder {
    private ItemStack item;

    public ItemBuilder createItem() {
        item = new ItemStack(Material.AIR);
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = item.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(displayName);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        Objects.requireNonNull(itemMeta).setLore(lore);
        setItemMeta(itemMeta);
        return this;
    }

    private void setItemMeta(ItemMeta itemMeta) {
        item.setItemMeta(itemMeta);
    }

    public ItemStack build() {
        return item;
    }
}
