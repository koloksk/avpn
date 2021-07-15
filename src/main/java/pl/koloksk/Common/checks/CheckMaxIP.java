package pl.koloksk.Common.checks;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.koloksk.Bungee.Main;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

public class CheckMaxIP {


    public static boolean check(String hostname) {
        int clients = 0;
        if(!StoreData.bungeeEnabled) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (hostname.equalsIgnoreCase(player.getAddress().getHostName()))
                    clients++;
            }
        } else {
            for (ProxiedPlayer p : Main.getinstance().getPlayers()){
                if(hostname.equalsIgnoreCase(p.getAddress().getHostName()))
                    clients++;
            }
        }
        return clients >= Settings.maxip_limit;
    }
}
