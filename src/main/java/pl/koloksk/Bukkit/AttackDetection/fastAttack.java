package pl.koloksk.Bukkit.AttackDetection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

import static pl.koloksk.Common.Config.Config.detect_minjps;

public class fastAttack {
    public static void check(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (StoreData.ilosc_polaczen / 2 > detect_minjps && !StoreData.fastAttack && !StoreData.slowAttack) {
                    StoreData.fastAttack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    LogFilter.enableFilter();
                } else if (StoreData.ilosc_polaczen / 2 < detect_minjps && StoreData.fastAttack && !StoreData.slowAttack) {
                    StoreData.fastAttack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    LogFilter.disableFilter();
/*
                    StoreData.AttackJoin.clear();
*/
                }
                if (StoreData.fastAttack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + StoreData.ilosc_polaczen / 2 + "/s");
                StoreData.ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(Main.getinstance(), 20L, 20 * 2);

    }
}
