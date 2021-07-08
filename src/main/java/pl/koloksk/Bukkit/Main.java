package pl.koloksk.Bukkit;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Listeners.AsyncPlayerPreLoginEvent;
import pl.koloksk.Bukkit.Listeners.PlayerLoginEvent;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;
import java.util.List;

import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;


//TODO
// - auto download country database
// - auto download asn database -
// - block nicknames template
// - auto update checks
// - iptables integration
// - console filter
// - http://api.blocklist.de/api.php?ip=51.77.36.199&start=1
// - ipsum above 1


public class Main extends JavaPlugin {
    public static File orgdatabase = new File("plugins/Anti-vpn/GeoLite2-ASN.mmdb");
    public static File codatabase = new File("plugins/Anti-vpn/GeoLite2-Country.mmdb");


    public static List<String> Country_list;
    public static boolean attack = false;
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), this);

        this.getCommand("avpn").setExecutor(new Commands(this));
        StoreData.blocked = this.getConfig().getInt("stats.blocked");
        loadConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    LoadDB.loaddb();
                    LoadDB.downloaddb();

                } catch (NullPointerException ignored) {
                }

            }
        }.runTaskAsynchronously(this);

        checkattack();


    }
    @Override
    public void onDisable() {
        getConfig().set("stats.blocked", StoreData.blocked);
        saveConfig();
    }

    public void checkattack(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ilosc_polaczen / 5 > 5 && !attack) {
                    attack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    Logger logger = (Logger) LogManager.getRootLogger();
                    logger.addFilter(new LogFilter());
                } else if (ilosc_polaczen / 5 < 5 && attack) {
                    attack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    Logger logger = (Logger) LogManager.getRootLogger();
                    logger.addFilter(null);
                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_polaczen / 5 + "/s");
                ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(this, 20L, 20 * 5);

    }


    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Country_list = getConfig().getStringList("Country.list");
        StoreData.blockedNicks = getConfig().getStringList("block_nick.list");

    }






/*    public void punish(String ip) {
        List<String> words = this.getConfig().getStringList("Lista");
        words.add(ip);
        this.getConfig().set("Lista", words);

    }*/





    public static Main getinstance(){
        return plugin;
    }

}
