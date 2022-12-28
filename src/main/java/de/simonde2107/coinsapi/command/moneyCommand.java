package de.simonde2107.coinsapi.command;

import de.simonde2107.coinsapi.util.CoinsAPI;
import de.simonde2107.coinsapi.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class moneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Data.ONLY_INGAME + "with this arg. Use {/money [player]}");
                return true;
            }
            Player player = (Player) sender;
            player.sendMessage(Data.PREFIX + "§bDein Kontostand beträgt §e" + CoinsAPI.getCoins(player) + " €");
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null && target.isOnline()) {

                sender.sendMessage(Data.PREFIX + "§e" + target.getName() + " §7besitzt §a" + CoinsAPI.getCoins(target) + " €");
            } else {
                sender.sendMessage(Data.NOT_ONLINE);
            }
        } else {
            sender.sendMessage(Data.USAGE + "/money <Spieler>");
        }
        return true;
    }
}
