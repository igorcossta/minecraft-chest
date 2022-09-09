package io.github.igorcossta.commands;

import io.github.igorcossta.Main;
import io.github.igorcossta.commands.standard.ChestCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandManager implements CommandExecutor {
    private final Main plugin;
    private final Set<SubCommand> subCommands = new HashSet<>();

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        this.subCommands.add(new ChestCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (args.length > 0) {
                helpMenu(p);
            } else {

                subCommands.stream()
                        .filter(cmd -> cmd.commandName().equalsIgnoreCase(command.getName()))
                        .findFirst()
                        .ifPresent(cmd -> {
                            if (p.hasPermission(cmd.commandPermission())) cmd.perform(p, args);
                            else p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfig().getString("config.no_permission.player")));
                        });
            }

        } else sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("config.no_permission.console"))
        );
        return true;
    }

    private void helpMenu(Player p) {
        subCommands.forEach(i ->
                p.sendMessage("/" + i.commandName() + " - " + i.commandDescription())
        );
    }
}
