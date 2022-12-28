package de.simonde2107.coinsapi;

import de.simonde2107.coinsapi.command.coinsadmin;
import de.simonde2107.coinsapi.command.moneyCommand;
import de.simonde2107.coinsapi.util.Data;
import de.simonde2107.coinsapi.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinsAPI extends JavaPlugin {


    public static CoinsAPI plugin;
    public static MySQL mySQL;



    @Override
    public void onEnable() {
        plugin = this;
        mySQL = new MySQL("127.0.0.1", "3306", Data.sqlDBName, Data.sqlUser, Data.sqlPW);
        registerCommand();
        registerListener();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    void registerCommand() {
        getCommand("coinsadmin").setExecutor(new coinsadmin());
        getCommand("money").setExecutor(new moneyCommand());
    }

    void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
