package pl.koloksk.Bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.koloksk.Common.checks.*;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;
import java.util.logging.Level;

public class Check {
    public static Main plugin = Main.getinstance();
    public static void Check(int a, String ip, AsyncPlayerPreLoginEvent e) throws IOException {
        AsyncPlayerPreLoginEvent.Result kick = AsyncPlayerPreLoginEvent.Result.KICK_OTHER;
        FileConfiguration config = plugin.getConfig();

        switch(a) {
            case 1:
                Bukkit.broadcastMessage(e.getName());
                if (CheckNick.check(e.getName()) && config.getBoolean("block_nick.enabled")) {
                    e.disallow(kick, config.getString("Messages.block-vpn"));
                    StoreData.blocked++;
                    if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bNICK (" + ip + ")");
                    break;

                    //punish(ip);
                } else {
                    Check(2, ip, e);
                    break;

                }
            case 2:
                if (CheckORG.check(ip) && config.getBoolean("Organization.enabled")) {
                    e.disallow(kick, config.getString("Messages.block-vpn"));
                    StoreData.blocked++;
                    if (config.getBoolean("Debug"))
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bORG (" + ip + ")");
                    break;

                    //punish(ip);
                } else {
                    Check(3, ip, e);
                    break;

                }
            case 3:
                if (CheckCountry.check(ip) && config.getBoolean("Country.enabled")) {
                    if (config.getBoolean("Country.only-attack")) {
                        if (Main.attack) {
                            e.disallow(kick, config.getString("Messages.block-country"));
                            StoreData.blocked++;

                            if (config.getBoolean("Debug"))
                                Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-country"));
                        StoreData.blocked++;

                        if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    Check(4, ip, e);
                    break;
                }
            case 4:
                if (CheckMaxIP.check(e.getAddress().getHostName(), plugin) && config.getBoolean("max-join-per-ip.enabled")) {
                    if (config.getBoolean("max-join-per-ip.only-attack")) {
                        if (Main.attack) {
                            e.disallow(kick, config.getString("Messages.block-max-connections"));
                            StoreData.blocked++;

                            if (config.getBoolean("Debug"))
                                Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-max-connections"));
                        StoreData.blocked++;

                        if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    Check(5, ip, e);
                    break;

                }
            case 5:
                if (CheckIPblacklist.check(ip) && config.getBoolean("ip-list-block.enabled")) {
                    if (config.getBoolean("ip-list-block.only-attack")) {
                        if (Main.attack) {
                            e.disallow(kick, config.getString("Messages.block-ip-list"));
                            StoreData.blocked++;

                            if (config.getBoolean("Debug"))
                                Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bproxy-list (" + ip + ")");
                            break;

                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-ip-list"));
                        StoreData.blocked++;

                        if (config.getBoolean("Debug"))
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bproxy-list (" + ip + ")");
                        //punish(ip);
                        break;

                    }
                } else {
                    Check(6, ip, e);
                    break;

                }
            case 6:
                if (config.getBoolean("api.enabled")) {
                    if (CheckIpIntel.check(ip)) {
                        e.disallow(kick, config.getString("Messages.block-vpn"));
                        StoreData.blocked++;
                        if (config.getBoolean("Debug")) {

                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bAPI (" + ip + ")");
                        }
                        //punish(ip);
                        break;
                    }
                } else {
                    Check(7, ip, e);
                    break;

                }
            case 7:
                if (config.getBoolean("api.enabled")) {
                    if (CheckVPNapi.check(ip)) {
                        e.disallow(kick, config.getString("Messages.block-vpn"));
                        StoreData.blocked++;

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
}
