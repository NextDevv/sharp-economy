package me.plugins.sharpeconomy.commands;

import me.plugins.sharpeconomy.SharpEconomy;
import me.plugins.sharpeconomy.commands.subcommands.Pay;
import me.plugins.sharpeconomy.commands.subcommands.show;
import me.plugins.sharpeconomy.db.DataBase;
import me.plugins.sharpeconomy.service.StringFormatService;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getName;

public class CommandManager implements TabExecutor {
    SharpEconomy plugin;
    String PluginPrefix = "[SharpEconomy] ";
    Configuration config;

    StringFormatService StringService = new StringFormatService();

    public CommandManager(SharpEconomy plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            String title = getConfigString("default-command-prefix");
                title = translateAlternateColorCodes(title);
            if(command.getName().equalsIgnoreCase("balance") && args.length > 0) {
                switch (args[0]) {
                    case "show":
                        new show().actionPerformed(player, args);
                    case "pay":
                        new Pay().actionPerformed(player, args);
                }
            }else if(command.getName().equalsIgnoreCase("balance")) {
                player.sendMessage(title);
                String line1 = StringService.getConfigString("default-command-response-L2");
                    line1 = StringService.translateAlternateColorCodes(line1);
                player.sendMessage(line1);

                String line2 = StringService.getConfigString("default-command-response-L3");
                    line2 = StringService.translateAlternateColorCodes(line2);
                player.sendMessage(line2);

                if(player.isOp()) {
                    String line3 = StringService.getConfigString("default-command-response-L4");
                        line3 = StringService.translateAlternateColorCodes(line3);
                    player.sendMessage(line3);

                    String line4 = StringService.getConfigString("default-command-response-L5");
                        line4 = StringService.translateAlternateColorCodes(line4);
                    player.sendMessage(line4);
                }
            }


        }else if(sender instanceof ConsoleCommandSender) {
            if(command.getName().equalsIgnoreCase("isconnected")) {
                if(plugin.database != null) {
                    System.out.println(PluginPrefix + "The connection is already established");
                }else {
                    System.out.println(PluginPrefix + "The connection is not established");
                }
            }
            if(command.getName().equalsIgnoreCase("balance")) {
                System.out.println(PluginPrefix + "You can use OP commands from the console");
                System.out.println(PluginPrefix + "Commands available: balance add | balance remove | balance set");
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("add") && !Objects.equals(args[1], "") && !Objects.equals(args[2], "")) {
                        System.out.println(PluginPrefix + "Added $" + args[1] + " to " + args[2]);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public String translateAlternateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String getConfigString(String path) {
        return plugin.getConfig().getString(path);
    }
}
