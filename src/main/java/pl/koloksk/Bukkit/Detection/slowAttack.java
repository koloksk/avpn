package pl.koloksk.Bukkit.Detection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

import static pl.koloksk.Common.utils.Settings.detect_minjps;
import static pl.koloksk.Common.utils.StoreData.*;

public class slowAttack {
    public static void check(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ilosc_blokad / 2 > detect_minjps && !attack) {
                    attack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    LogFilter.enableFilter();
                } else if (ilosc_blokad / 2 < detect_minjps && attack) {
                    attack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    LogFilter.disableFilter();
                    StoreData.AttackJoin.clear();
                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_blokad / 2 + "/s");
                ilosc_blokad = 0;


            }
        }.runTaskTimerAsynchronously(Main.getinstance(), 20L, 20 * 2);

    }
}
