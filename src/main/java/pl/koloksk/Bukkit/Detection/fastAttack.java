package pl.koloksk.Bukkit.Detection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

import static pl.koloksk.Common.utils.Settings.detect_minjps;
import static pl.koloksk.Common.utils.StoreData.attack;
import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class fastAttack {
    public static void check(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ilosc_polaczen / 2 > detect_minjps && !attack && !StoreData.slowAttack) {
                    attack = true;
                    StoreData.fastAttack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    LogFilter.enableFilter();
                } else if (ilosc_polaczen / 2 < detect_minjps && attack && !StoreData.slowAttack) {
                    attack = false;
                    StoreData.fastAttack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    LogFilter.disableFilter();
/*
                    StoreData.AttackJoin.clear();
*/
                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_polaczen / 2 + "/s");
                ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(Main.getinstance(), 20L, 20 * 2);

    }
}
