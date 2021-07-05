package pl.koloksk.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.koloksk.Main;

public class CheckMaxIP {


    public static boolean check(String hostname, Main plugin) {
        int clients = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hostname.equalsIgnoreCase(player.getAddress().getHostName()))
                clients++;
        }
        return clients >= plugin.getConfig().getInt("max-join-per-ip.limit");
    }
}
