package io.github.igorcossta.menus;

import io.github.igorcossta.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {
    protected final Main plugin;
    protected Inventory inventory;
    protected final MenuOwner menuOwner;

    public Menu(Main plugin, MenuOwner menuOwner) {
        this.plugin = plugin;
        this.menuOwner = menuOwner;
    }

    public abstract String menuTitle();

    public abstract Row menuRows();

    public abstract void handle(InventoryEvent e);

    protected abstract void drawMenu();

    public final void open() {
        inventory = Bukkit.createInventory(this, this.menuRows().getSize(), this.menuTitle());
        this.drawMenu();
        menuOwner.getPlayer().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
