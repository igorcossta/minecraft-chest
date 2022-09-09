package io.github.igorcossta.menus.standard;

import io.github.igorcossta.Main;
import io.github.igorcossta.menus.Menu;
import io.github.igorcossta.menus.MenuOwner;
import io.github.igorcossta.menus.Row;
import io.github.igorcossta.menus.Slot;
import io.github.igorcossta.utils.ItemBuilder;
import io.github.igorcossta.utils.ItemWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class ChestMenu extends Menu {
    private int chestNumber;

    public ChestMenu(Main plugin, MenuOwner menuOwner) {
        super(plugin, menuOwner);
    }

    public ChestMenu(Main plugin, MenuOwner menuOwner, int chestNumber) {
        super(plugin, menuOwner);
        this.chestNumber = chestNumber;
    }

    @Override
    public String menuTitle() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chest_title"));
    }

    @Override
    public Row menuRows() {
        return Row.SIX;
    }

    public int getChestNumber() {
        return chestNumber;
    }

    public void setChestNumber(int i) {
        this.chestNumber = i;
    }

    @Override
    public void handle(InventoryEvent e) {
        if (e instanceof InventoryClickEvent clickEvent) inventoryClickEvent(clickEvent);

        if (e instanceof InventoryOpenEvent openEvent) inventoryOpenEvent(openEvent);

        if (e instanceof InventoryCloseEvent closeEvent) inventoryCloseEvent(closeEvent);
    }

    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getView().getBottomInventory().equals(e.getClickedInventory())) return;
        if (Slot.STANDARD.getSlots().contains(e.getRawSlot())) return;
        e.setCancelled(true);

        int clickedSlot = e.getRawSlot();
        Inventory topInventory = e.getView().getTopInventory();

        switch (clickedSlot) {
            case 8 -> swap(0, topInventory);
            case 17 -> swap(1, topInventory);
            case 48 -> deleteChest(topInventory);
            case 46 -> {
                moveItemsToPlayerInventory(topInventory);
                deleteChest(topInventory);
            }
        }
    }

    private void deleteChest(Inventory currentChest) {
        for (Integer slot : Slot.STANDARD.getSlots()) {
            currentChest.setItem(slot, new ItemStack(Material.AIR));
        }
        menuOwner.getChests().put(getChestNumber(), new ItemWrapper());
    }

    private void inventoryOpenEvent(InventoryOpenEvent e) {
        if (!menuOwner.getChests().containsKey(getChestNumber())) return;

        ItemWrapper items = menuOwner.getChest(getChestNumber());

        // if chest is empty do nothing
        if (items.getItems().isEmpty()) return;

        Inventory inv = e.getView().getTopInventory();
        items.getItems().forEach(inv::setItem);
    }

    private void inventoryCloseEvent(InventoryCloseEvent e) {
        Inventory inv = e.getView().getTopInventory();
        if (inv.isEmpty()) return;

        ItemWrapper items = getChestItems(inv);
        menuOwner.saveChest(getChestNumber(), items);
    }

    @Override
    protected void drawMenu() {
        ItemBuilder itemBuilder = new ItemBuilder();
        // fill system slots
        ItemStack pane = itemBuilder.createItem()
                .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName(ChatColor.DARK_GRAY + menuOwner.getPlayer().getDisplayName())
                .build();
        for (Integer slot : Slot.SYSTEM.getSlots()) {
            getInventory().setItem(slot, pane);
        }

        // draw the action buttons
        ItemStack hopper = itemBuilder.createItem()
                .setMaterial(Material.HOPPER)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("move_items_button")))
                .build();

        getInventory().setItem(46, hopper);

        ItemStack redStone = itemBuilder.createItem()
                .setMaterial(Material.REDSTONE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("clear_items_button")))
                .build();

        getInventory().setItem(48, redStone);

        // draw the purchasable chests
        int i = 0;
        for (Integer slot : Slot.PURCHASABLE_CHESTS.getSlots()) {
            i++;
            ItemStack chest = itemBuilder.createItem()
                    .setMaterial(Material.CHEST)
                    .setDisplayName(ChatColor.GRAY + "Chest #" + i)
                    .build();
            getInventory().setItem(slot, chest);
        }

    }

    private ItemWrapper getChestItems(Inventory inv) {
        ItemWrapper itemWrappers = new ItemWrapper();
        for (int i = 0; i < 54; i++) {
            if (!Slot.STANDARD.getSlots().contains(i)) continue;
            itemWrappers.getItems().put(i, inv.getItem(i));
        }
        return itemWrappers;
    }

    private void swap(int nextChest, Inventory view) {
        ItemWrapper currentChest = getChestItems(view);
        if (!currentChest.getItems().isEmpty()) {
            menuOwner.saveChest(getChestNumber(), currentChest);
            clear(currentChest, view);
        }

        ItemWrapper newChest = menuOwner.getChest(nextChest);
        newChest.getItems().forEach(view::setItem);
        setChestNumber(nextChest);
    }

    private void clear(ItemWrapper currentChest, Inventory viewToClear) {
        currentChest.getItems().forEach((slot, item) -> viewToClear.remove(item));
    }

    private void moveItemsToPlayerInventory(Inventory currentChest) {
        ItemWrapper chestItems = getChestItems(currentChest);

        if (chestItems.getItems().isEmpty()) return;

        // remove items from chest and add to player's inventory
        // https://www.spigotmc.org/threads/how-to-check-if-players-inventory-can-hold-or-not-new-itemstack.340455/
        ItemStack[] itemsToMove = chestItems.getItems().values().stream().filter(Objects::nonNull).toArray(ItemStack[]::new);
        Player player = menuOwner.getPlayer();
        HashMap<Integer, ItemStack> toDrop = player.getInventory().addItem(itemsToMove);
        if (!toDrop.isEmpty()) {
            toDrop.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
            player.playSound(player.getLocation(), Sound.ITEM_BUNDLE_DROP_CONTENTS, 1F, 1F);
        }
    }
}
