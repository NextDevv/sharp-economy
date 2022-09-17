package me.plugins.sharpeconomy.commands.subcommands;

import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {
    String getName();
    String getDescription();
    String getSyntax();
    void actionPerformed(Player player, String[] args);
    List<String> getSubcommands();
}
