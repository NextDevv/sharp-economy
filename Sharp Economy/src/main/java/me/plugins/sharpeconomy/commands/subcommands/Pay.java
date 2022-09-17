package me.plugins.sharpeconomy.commands.subcommands;

import me.plugins.sharpeconomy.db.DataBase;
import me.plugins.sharpeconomy.models.PlayerData;
import me.plugins.sharpeconomy.service.StringFormatService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Pay implements SubCommand{
    StringFormatService service = new StringFormatService();

    @Override
    public String getName() {
        return "Pay";
    }

    @Override
    public String getDescription() {
        return "Pays the player";
    }

    @Override
    public String getSyntax() {
        return "/balance pay <player> <amount>";
    }

    @Override
    public void actionPerformed(Player player, String[] args) {
        if(args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                String error = service.getConfigString("balance-error-name");
                    error = service.translateAlternateColorCodes(error);
                player.sendMessage(error);
                return;
            }else if(target.getUniqueId() == player.getUniqueId()) {
                String error = service.getConfigString("pay-balance-self-error");
                    error = service.translateAlternateColorCodes(error);
                player.sendMessage(error);
                return;
            }

            PlayerData data = DataBase.getPlayerFromUUID(target.getUniqueId().toString());
            PlayerData data2 = DataBase.getPlayerFromUUID(player.getUniqueId().toString());
            long balance = data.getBalance();
            long balance2 = data2.getBalance();

            try {
                DataBase dataBase = new DataBase();

                long payAmount = Long.parseLong(args[2]);
                data2.setBalance(data2.getBalance() - payAmount);

                data.setBalance(data.getBalance() + payAmount);

                String playerSuccess = service.getConfigString("pay-balance-success");
                    playerSuccess = service.replace(playerSuccess, "%amount%", String.valueOf(payAmount));
                    playerSuccess = service.replace(playerSuccess, "%target%", target.getDisplayName());
                    playerSuccess = service.translateAlternateColorCodes(playerSuccess);
                player.sendMessage(playerSuccess);

                String targetSuccess = service.getConfigString("pay-balance-target");
                    targetSuccess = service.replace(targetSuccess, "%amount%", String.valueOf(payAmount));
                    targetSuccess = service.replace(targetSuccess, "%player%", player.getDisplayName());
                    targetSuccess = service.translateAlternateColorCodes(targetSuccess);
                target.sendMessage(targetSuccess);

                dataBase.updatePlayerDatabase(data2);
                dataBase.updatePlayerDatabase(data);
            }catch (NumberFormatException e) {
                String error = service.getConfigString("balance-error-format");
                    error = service.translateAlternateColorCodes(error);
                player.sendMessage(error);
            }
        }else {

        }
    }

    @Override
    public List<String> getSubcommands() {
        return null;
    }
}
