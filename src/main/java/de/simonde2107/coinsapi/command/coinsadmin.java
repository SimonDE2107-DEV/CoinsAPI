package de.simonde2107.coinsapi.command;

import de.simonde2107.coinsapi.util.CoinsAPI;
import de.simonde2107.coinsapi.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class coinsadmin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("coinsapi.coinsadmin")) {
            if (args.length == 3) {
                Integer amount = null;
                Player target = Bukkit.getPlayer(args[1]);
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(Data.PREFIX + "§cBitte gebe eine Zahl ein!");
                }
                if (target != null && target.isOnline()) {
                    if (args[0].equalsIgnoreCase("set")) {
                        CoinsAPI.setCoins(sender, target, amount);
                    } else if (args[0].equalsIgnoreCase("add")) {
                        CoinsAPI.addCoins(sender, target, amount);
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        CoinsAPI.removeCoins(sender, target, amount);
                    } else {
                        sender.sendMessage(usage());
                    }
                } else {
                    sender.sendMessage(Data.NOT_ONLINE);
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("get")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    sender.sendMessage(Data.PREFIX + "§e" + target.getName() + " §7besitzt §a" + CoinsAPI.getCoins(target) + " €");
                } else {
                    sender.sendMessage(usage());
                }
            } else {
                sender.sendMessage(usage());
            }
        } else {
            sender.sendMessage(Data.NO_PERMISSIONS);
        }
        return true;
    }

    String usage() {
        return Data.USAGE + "/coinsadmin [set|add|get|remove] [Spieler] <Anzahl>";
    }
}
