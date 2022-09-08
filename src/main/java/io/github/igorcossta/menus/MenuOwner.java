package io.github.igorcossta.menus;

import io.github.igorcossta.utils.ItemWrapper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MenuOwner {
    private final Player player;
    private Map<Integer, ItemWrapper> chests = new HashMap<>();

    public MenuOwner(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<Integer, ItemWrapper> getChests() {
        return chests;
    }

    public ItemWrapper getChest(int chestNumber) {
        if (!getChests().containsKey(chestNumber)) return new ItemWrapper();
        return chests.get(chestNumber);
    }

    public void saveChest(Integer chestNumber, ItemWrapper items) {
        getChests().put(chestNumber, items);
    }

}
