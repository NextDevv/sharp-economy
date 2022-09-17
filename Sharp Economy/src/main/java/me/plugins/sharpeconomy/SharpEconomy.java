package me.plugins.sharpeconomy;

import me.plugins.sharpeconomy.commands.CommandManager;
import me.plugins.sharpeconomy.db.DataBase;
import me.plugins.sharpeconomy.listeners.PlayerJoinListener;
import me.plugins.sharpeconomy.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SharpEconomy extends JavaPlugin {
    public DataBase database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.database = new DataBase();
        DataBase.InitilizeDatabase();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // Setting the commands executors
        getCommand("balance").setExecutor(new CommandManager(this));
        getCommand("isconnected").setExecutor(new CommandManager(this));

        // Creating the configuration file
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    public DataBase getDataBase() {
        return database;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        loadConfiguration();

        // Closing the database connection
        if(this.database != null) {
            database.closeConnection();
            System.out.println("Database connection closed");
        }
    }

    public static long getUserBalance(String uuid) {
        PlayerData data = DataBase.getPlayerFromUUID(uuid);
        return data == null? 0 : data.getBalance();
    }

    public static void setUserBalance(String uuid, long balance) {
        PlayerData data = DataBase.getPlayerFromUUID(uuid);
        if(data!= null) {
            data.setBalance(balance);
            new DataBase().updatePlayerDatabase(data);
        }
    }
}
