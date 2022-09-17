package me.plugins.sharpeconomy.commands.subcommands;

import me.plugins.sharpeconomy.SharpEconomy;
import me.plugins.sharpeconomy.db.DataBase;
import me.plugins.sharpeconomy.models.PlayerData;
import me.plugins.sharpeconomy.service.StringFormatService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class show implements SubCommand{
    SharpEconomy plugin = SharpEconomy.getPlugin(SharpEconomy.class);
    StringFormatService StringService = new StringFormatService();

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Shows the player balance";
    }

    @Override
    public String getSyntax() {
        return "/balance show <player>";
    }

    @Override
    public void actionPerformed(Player player, String[] args) {
        if(args.length == 1) {
            PlayerData data = DataBase.getPlayerFromUUID(player.getUniqueId().toString());

            String reply = plugin.getConfig().getString("show-balance-self");
                reply = StringService.replace(reply, "%amount%", String.valueOf(data.getBalance()));
                reply = StringService.translateAlternateColorCodes(reply);
            player.sendMessage(reply);

        }else if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                String error = StringService.getConfigString("balance-error-name");
                    error = StringService.translateAlternateColorCodes(error);
                player.sendMessage(error);
                return;
            }
            PlayerData targetData = DataBase.getPlayerFromUUID(target.getUniqueId().toString());
            long targetBalance = targetData.getBalance();
            String reply = StringService.getConfigString("show-balance-target");
                reply = StringService.translateAlternateColorCodes(reply);
                reply = StringService.replace(reply, "%target%", target.getDisplayName());
                reply = StringService.replace(reply, "%amount%", String.valueOf(targetBalance));
            player.sendMessage(reply);

        }
    }

    @Override
    public List<String> getSubcommands() {
        return null;
    }
}
