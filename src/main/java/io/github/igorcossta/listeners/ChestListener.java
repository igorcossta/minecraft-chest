package io.github.igorcossta.listeners;

import io.github.igorcossta.menus.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class ChestListener implements Listener {

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        handleEvent(e);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        handleEvent(e);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        handleEvent(e);
    }

    private void handleEvent(InventoryEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu menu)
            menu.handle(e);
    }
}
