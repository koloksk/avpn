package pl.koloksk.Common.checks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.koloksk.Common.utils.Settings;

public class CheckMaxIP {


    public static boolean check(String hostname) {
        int clients = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hostname.equalsIgnoreCase(player.getAddress().getHostName()))
                clients++;
        }
        return clients >= Settings.maxip_limit;
    }
}
