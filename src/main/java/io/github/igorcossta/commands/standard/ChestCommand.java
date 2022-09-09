package io.github.igorcossta.commands.standard;

import io.github.igorcossta.Main;
import io.github.igorcossta.commands.SubCommand;
import io.github.igorcossta.menus.Menu;
import io.github.igorcossta.menus.MenuOwner;
import io.github.igorcossta.menus.standard.ChestMenu;
import org.bukkit.entity.Player;

public class ChestCommand implements SubCommand {
    private final Main plugin;

    public ChestCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String commandName() {
        return "chest";
    }

    @Override
    public String commandDescription() {
        return plugin.getConfig().getString("config.chest.description");
    }

    @Override
    public String commandPermission() {
        return plugin.getConfig().getString("config.chest.permission");
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length < 1) {
            MenuOwner menuOwner = plugin.getPlayerChest(p);
            Menu menu = new ChestMenu(plugin, menuOwner, 0);
            menu.open();
        }
    }
}
