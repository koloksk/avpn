package pl.koloksk.Bukkit;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Listeners.AsyncPlayerPreLoginEvent;
import pl.koloksk.Common.Metrics.Metrics;
import pl.koloksk.Common.utils.LogFilter;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;

import static pl.koloksk.Common.utils.Settings.*;
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
// -


public class Main extends JavaPlugin {
    public static File orgdatabase = new File("plugins/Anti-vpn/GeoLite2-ASN.mmdb");
    public static File codatabase = new File("plugins/Anti-vpn/GeoLite2-Country.mmdb");

    public static boolean attack = false;
    public static Main plugin;
    @Override
    public void onEnable() {


        int pluginId = 12002; // <-- Replace with the id of your plugin!
        new Metrics(this, pluginId);

        // Optional: Add custom charts


        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginEvent(), this);
        //Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), this);

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
                if (ilosc_polaczen / 2 > detect_minjps && !attack) {
                    attack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    LogFilter.enableFilter();
                } else if (ilosc_polaczen / 2 < detect_minjps && attack) {
                    attack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    LogFilter.disableFilter();
                    StoreData.AttackJoin.clear();
                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_polaczen / 2 + "/s");
                ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(this, 20L, 20 * 2);

    }


    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        settings();


    }
    public void settings() {
        permissions_admin = getConfig().getString("permissions.admin");
        permissions_bypass = getConfig().getString("permissions.bypass");

        Messages_country = getConfig().getString("messages.country");
        Messages_vpn = getConfig().getString("messages.vpn");
        Messages_maxip = getConfig().getString("messages.max-connections");
        Messages_iplist = getConfig().getString("messages.iplist");


        contry_enabled = getConfig().getBoolean("country.enabled");
        contry_attack = getConfig().getBoolean("country.only-attack");
        contry_whitelist = getConfig().getBoolean("country.whitelist");
        contry_list = getConfig().getStringList("country.list");

        asn_enabled = getConfig().getBoolean("asn.enabled");
        asn_attack = getConfig().getBoolean("asn.only-attack");;

        maxip_enabled = getConfig().getBoolean("max-join-per-ip.enabled");
        maxip_limit = getConfig().getInt("max-join-per-ip.limit");;

        iplist_enabled = getConfig().getBoolean("ip-list.enabled");
        iplist_attack = getConfig().getBoolean("ip-list.only-attack");


        api_enabled = getConfig().getBoolean("api.enabled");

        blocknick_enabled = getConfig().getBoolean("block_nick.enabled");
        blocknick_list = getConfig().getStringList("block_nick.list");;

        detect_minjps = getConfig().getInt("detect_attack.min-jps");

        integration_authme_enabled = getConfig().getBoolean("integrations.authme.enabled");
        integration_authme_kick = getConfig().getString("integrations.authme.kick");

        integration_discord_enabled = getConfig().getBoolean("integrations.discord.enabled");
        integration_discord_url = getConfig().getString("integrations.discord.webhook-url");

        debug = getConfig().getBoolean("Debug");

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
