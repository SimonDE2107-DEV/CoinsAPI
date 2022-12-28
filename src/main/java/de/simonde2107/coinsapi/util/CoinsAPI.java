package de.simonde2107.coinsapi.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsAPI {

    public static void addCoins(CommandSender player, Player target, int amount) {
        int oldAmount = getCoins(target);
        MySQL.setCoins(target,oldAmount+amount);
        target.sendMessage("§dDir wurden §e" + amount + " € §dhinzugefügt.");
        player.sendMessage("§dDu hast §e" + target.getName() + amount + " € §dhinzugefügt.");
    }

    public static void removeCoins(CommandSender player, Player target, int amount) {
        int oldAmount = getCoins(target);
        MySQL.setCoins(target,oldAmount-amount);
        target.sendMessage("§dDir wurden §e" + amount + " € §dabgezogen.");
        player.sendMessage("§dDu hast §e" + target.getName() + amount + " € §dabgezogen.");
    }

    public static void setCoins(CommandSender player, Player target, int amount) {
        MySQL.setCoins(target,amount);
        target.sendMessage("§dDein Kontostand wurde auf §e" + amount + " € §dgesetzt.");
        player.sendMessage("§dDu hast den Kontostand von §e" + target.getName() + " §dauf §e" + amount + " € §dgesetzt.");
    }

    public static Integer getCoins(Player player) {
        return MySQL.getCoins(player);
    }
}
