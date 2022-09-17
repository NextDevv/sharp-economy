package me.plugins.sharpeconomy.service;

import me.plugins.sharpeconomy.SharpEconomy;
import org.bukkit.ChatColor;

public class StringFormatService {
    SharpEconomy plugin = SharpEconomy.getPlugin(SharpEconomy.class);

    public String replace(String text, String s, String newValue) {
        return text.replace(s, newValue);
    }

    public String translateAlternateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String getConfigString(String path) {
        return plugin.getConfig().getString(path);
    }
}
