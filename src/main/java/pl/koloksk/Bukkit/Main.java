package pl.koloksk.Bukkit;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.AttackDetection.fastAttack;
import pl.koloksk.Bukkit.AttackDetection.slowAttack;
import pl.koloksk.Bukkit.Listeners.AsyncPlayerPreLoginEvent;
import pl.koloksk.Common.Config.ConfigManager;
import pl.koloksk.Common.Metrics.MetricsLite;
import pl.koloksk.Common.utils.DB.LoadDB;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;


//TODO
// - auto update checks
// - iptables integration
// - console filter
// - http://api.blocklist.de/api.php?ip=51.77.36.199&start=1
// - ipsum above 1
// -


public class Main extends JavaPlugin {
    public static boolean AuthmeStatus;
    public static Main plugin;
    public static String mcver;
    @Override
    public void onEnable() {

        AuthmeStatus = Bukkit.getPluginManager().isPluginEnabled("AuthMe");
        StoreData.orgdatabase = new File("plugins/avpn/GeoLite2-ASN.mmdb");
        StoreData.codatabase = new File("plugins/avpn/GeoLite2-Country.mmdb");
        registerMetrics();

            String bukkitver = Bukkit.getServer().getVersion();
            int idx = bukkitver.indexOf("(MC: ");
            if(idx > 0) {
                mcver = bukkitver.substring(idx+5);
                idx = mcver.indexOf(")");
                if(idx > 0) mcver = mcver.substring(0, idx);
            }


        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginEvent(), this);
        this.getCommand("avpn").setExecutor(new Commands(this));
        //loadConfig();
        ConfigManager.loadConfig();
        LoadDB.downloaddb();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    LoadDB.loaddb();


                } catch (NullPointerException ignored) {
                }

            }
        }.runTaskAsynchronously(this);
        fastAttack.check();
        slowAttack.check();


    }
    @Override
    public void onDisable() {
        getConfig().set("stats.blocked", StoreData.blocked);
        saveConfig();
    }


    public void registerMetrics(){
        int pluginId = 12002; // <-- Replace with the id of your plugin!
        new MetricsLite(this, pluginId);
    }


    public static Main getinstance(){
        return plugin;
    }

}
