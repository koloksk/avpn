package pl.koloksk.Bukkit;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.Common.checks.*;
import pl.koloksk.Common.utils.LogFilter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

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


public class Main extends JavaPlugin implements Listener {
    public static File orgdatabase = new File("plugins/Anti-vpn/GeoLite2-ASN.mmdb");
    public static File codatabase = new File("plugins/Anti-vpn/GeoLite2-Country.mmdb");


    public static List<String> Country_list;
    public static boolean attack = false;
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(this, this);
        this.getCommand("avpn").setExecutor(new Commands(this));
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


        loadConfig();
        Country_list = this.getConfig().getStringList("Country.list");
        checkattack();


    }

    public void checkattack(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ilosc_polaczen / 5 > 5 && !attack) {
                    attack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                    Bukkit.getLogger().info("Serwer jest atakowany!!!");
                    Logger logger = (Logger) LogManager.getRootLogger();
                    logger.addFilter(new LogFilter());
                } else if (ilosc_polaczen / 5 < 5 && attack) {
                    attack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");
                    Bukkit.getLogger().info("Serwer nie jest już atakowany");
                    Logger logger = (Logger) LogManager.getRootLogger();
                    logger.addFilter(null);
                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_polaczen / 5 + "/s");
                Bukkit.getLogger().info("Ilosc polaczen na sek: " + ilosc_polaczen / 5 + "/s");
                ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(this, 20L, 20 * 5);

    }


    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Country_list = this.getConfig().getStringList("Country.list");

    }




    @EventHandler
    public void Onjoin(AsyncPlayerPreLoginEvent e) throws IOException {

        ilosc_polaczen++;
        check(1, e.getAddress().getHostAddress(), e);
    }

/*    public void punish(String ip) {
        List<String> words = this.getConfig().getStringList("Lista");
        words.add(ip);
        this.getConfig().set("Lista", words);

    }*/


    public void check(int a, String ip, AsyncPlayerPreLoginEvent e) throws IOException{
        AsyncPlayerPreLoginEvent.Result kick = AsyncPlayerPreLoginEvent.Result.KICK_FULL;
        FileConfiguration config = this.getConfig();

        switch(a) {
            case 1:
                if (CheckORG.check(ip) && config.getBoolean("Organization.enabled")) {
                    e.disallow(kick, config.getString("Messages.block-vpn"));
                    if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bORG (" + ip + ")");
                    break;

                    //punish(ip);
                } else {
                    check(2, ip, e);
                    break;

                }
            case 2:
                if (CheckCountry.check(ip) && config.getBoolean("Country.enabled")) {
                    if (config.getBoolean("Country.only-attack")) {
                        if (attack) {
                            e.disallow(kick, config.getString("Messages.block-country"));
                            if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-country"));
                        if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    check(3, ip, e);
                    break;
                }
            case 3:
                if (CheckMaxIP.check(e.getAddress().getHostName(), this) && config.getBoolean("max-join-per-ip.enabled")) {
                    if (config.getBoolean("max-join-per-ip.only-attack")) {
                        if (attack) {
                            e.disallow(kick, config.getString("Messages.block-max-connections"));
                            if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-max-connections"));
                        if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    check(4, ip, e);
                    break;

                }
            case 4:
                if (CheckIPblacklist.check(ip) && config.getBoolean("ip-list-block.enabled")) {
                    if (config.getBoolean("ip-list-block.only-attack")) {
                        if (attack) {
                            e.disallow(kick, config.getString("Messages.block-ip-list"));
                            if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bproxy-list (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-ip-list"));
                        if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bproxy-list (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    check(5, ip, e);
                    break;

                }
            case 5:
                if (config.getBoolean("api.enabled")) {
                    if (CheckIpIntel.check(ip)) {
                        e.disallow(kick, config.getString("Messages.block-vpn"));
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bAPI (" + ip + ")");
                        //punish(ip);
                        break;
                    }
                } else {
                    check(6, ip, e);
                    break;

                }
            case 6:
                if (config.getBoolean("api.enabled")) {
                    if (CheckVPNapi.check(ip)) {
                        e.disallow(kick, config.getString("Messages.block-vpn"));
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bAPI (" + ip + ")");
                        //punish(ip);
                        break;

                    } else {
                        System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
                    }
                    break;
                } else {
                    System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
                    break;
                }
        }

    }


    public static Main getinstance(){
        return plugin;
    }

}
