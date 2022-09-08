package io.github.igorcossta.commands;

import org.bukkit.entity.Player;

public interface SubCommand {
    String commandName();
    String commandDescription();
    String commandPermission();
    void perform(Player p, String[] args);
}
