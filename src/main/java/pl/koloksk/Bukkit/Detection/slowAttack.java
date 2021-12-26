package pl.koloksk.Bukkit.Detection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

public class slowAttack {
    public static void check(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (StoreData.ilosc_blokad >= 2 && !StoreData.attack && !StoreData.fastAttack) {
                    StoreData.attack = true;
                    StoreData.slowAttack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    LogFilter.enableFilter();
                } else if (StoreData.ilosc_blokad < 2 && StoreData.attack && !StoreData.fastAttack) {
                    StoreData.attack = false;
                    StoreData.slowAttack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    LogFilter.disableFilter();
/*
                    StoreData.AttackJoin.clear();
*/
                }

            }
        }.runTaskTimerAsynchronously(Main.getinstance(), 20L, 20 * 5);

    }
}
