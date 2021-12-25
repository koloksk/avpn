package pl.koloksk.Bukkit;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Bukkit.Detection.fastAttack;
import pl.koloksk.Bukkit.Detection.slowAttack;
import pl.koloksk.Bukkit.Listeners.AsyncPlayerPreLoginEvent;
import pl.koloksk.Common.Metrics.Metrics;
import pl.koloksk.Common.utils.LoadDB;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;

import static pl.koloksk.Common.utils.Settings.*;


//TODO
// - auto update checks
// - iptables integration
// - console filter
// - http://api.blocklist.de/api.php?ip=51.77.36.199&start=1
// - ipsum above 1
// -


public class Main extends JavaPlugin {
    public static File orgdatabase = new File("plugins/Anti-vpn/GeoLite2-ASN.mmdb");
    public static File codatabase = new File("plugins/Anti-vpn/GeoLite2-Country.mmdb");
    public static boolean AuthmeStatus;
    public static Main plugin;
    @Override
    public void onEnable() {

        AuthmeStatus = Bukkit.getPluginManager().isPluginEnabled("AuthMe");

        registerMetrics();

        plugin = this;
        LoadDB.plugin = this;
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginEvent(), this);
        //Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), this);
        LoadDB.downloaddb();
        this.getCommand("avpn").setExecutor(new Commands(this));
        loadConfig();
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
        new Metrics(this, pluginId);
    }

    public void loadConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        settings();
        LoadDB.loaddb();
    }

    public void reloadConfiguration(){
        reloadConfig();
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
        asn_attack = getConfig().getBoolean("asn.only-attack");

        maxip_enabled = getConfig().getBoolean("max-join-per-ip.enabled");
        maxip_limit = getConfig().getInt("max-join-per-ip.limit");

        iplist_enabled = getConfig().getBoolean("ip-list.enabled");
        iplist_attack = getConfig().getBoolean("ip-list.only-attack");


        api_enabled = getConfig().getBoolean("api.enabled");

        blocknick_enabled = getConfig().getBoolean("block_nick.enabled");
        blocknick_list = getConfig().getStringList("block_nick.list");

        detect_minjps = getConfig().getInt("detect_attack.min-jps");

        integration_authme_enabled = getConfig().getBoolean("integrations.authme.enabled");
        integration_authme_kick = getConfig().getString("integrations.authme.kick");

        integration_discord_enabled = getConfig().getBoolean("integrations.discord.enabled");
        integration_discord_url = getConfig().getString("integrations.discord.webhook-url");

        debug = getConfig().getBoolean("Debug");

    }


    public static Main getinstance(){
        return plugin;
    }

}
