package io.github.igorcossta;

import io.github.igorcossta.commands.CommandManager;
import io.github.igorcossta.listeners.ChestListener;
import io.github.igorcossta.menus.MenuOwner;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private Map<UUID, MenuOwner> playersChest = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("chest")).setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
    }

    @Override
    public void onDisable() {
        // save players chest state
    }

    public MenuOwner getPlayerChest(Player player) {
        if (!playersChest.containsKey(player.getUniqueId())) {
            MenuOwner menuOwner = new MenuOwner(player);
            playersChest.put(player.getUniqueId(), menuOwner);
            return menuOwner;
        }
        return playersChest.get(player.getUniqueId());
    }

}
