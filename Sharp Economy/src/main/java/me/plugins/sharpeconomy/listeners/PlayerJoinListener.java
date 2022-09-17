package me.plugins.sharpeconomy.listeners;

import me.plugins.sharpeconomy.SharpEconomy;
import me.plugins.sharpeconomy.models.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.Objects;

public class PlayerJoinListener implements Listener {
    private final SharpEconomy plugin;
    public PlayerJoinListener(SharpEconomy sharpEconomy) {
        this.plugin = sharpEconomy;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        PlayerData data = this.plugin.getDataBase().getPlayerFromUUID(player.getUniqueId().toString());

        if(data == null) {
            data = new PlayerData(player.getUniqueId().toString(), player.getDisplayName(), plugin.getConfig().getLong("start-balance"), new Date(),new Date());

            this.plugin.getDataBase().createPlayerDatabase(data);
        }else {
            if(data.getLastLogout().after(new Date())) {
                if(plugin.getConfig().getBoolean("daily-bonus-active")) {
                    long bonus = plugin.getConfig().getLong("daily-bonus");
                    String message = plugin.getConfig().getString("daily-bonus-message");
                        message = message.replace("%amount%", String.valueOf(bonus));
                        message = ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);

                    data.setBalance(data.getBalance()+bonus);
                }
            }

            data.setLast_login(new Date());
            this.plugin.getDataBase().updatePlayerDatabase(data);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        PlayerData data = this.plugin.getDataBase().getPlayerFromUUID(player.getUniqueId().toString());

        if(data == null) {
            data = new PlayerData(player.getUniqueId().toString(), player.getDisplayName(), plugin.getConfig().getLong("start-balance"), new Date(),new Date());

            this.plugin.getDataBase().createPlayerDatabase(data);
        }else {
            data.setLast_logout(new Date());
            this.plugin.getDataBase().updatePlayerDatabase(data);
        }
    }
}
