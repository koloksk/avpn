package pl.koloksk;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.koloksk.modules.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


//TODO
// - auto download country database
// - auto download asn database -
// - block nicknames template
// - auto update checks
// - iptables integration
// - console filter
// - http://api.blocklist.de/api.php?ip=51.77.36.199&start=1
// - ipsum above 1


public final class Main extends JavaPlugin implements Listener, CommandExecutor {
    public static File orgdatabase = new File("plugins/Anti-vpn/GeoLite2-ASN.mmdb");
    public static File codatabase = new File("plugins/Anti-vpn/GeoLite2-Country.mmdb");


    public static ArrayList<String> ASN_List;
    public static List<String> Country_list;
    public static List<String> listaip = new ArrayList<>();
    int ilosc_polaczen = 0;
    boolean attack = false;

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(this, this);
        this.getCommand("avpn").setExecutor(this);
        downloaddb();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    loaddb();

                } catch (NullPointerException ignored) {
                }

            }
        }.runTaskAsynchronously(this);


        loadConfig();
        Country_list = this.getConfig().getStringList("Country.list");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ilosc_polaczen / 5 > 5 && !attack) {
                    attack = true;
                    Bukkit.broadcastMessage("Serwer jest atakowany!!!");
                } else if (ilosc_polaczen / 5 < 5 && attack) {
                    attack = false;
                    Bukkit.broadcastMessage("Serwer nie jest już atakowany");

                }
                if (attack)
                    Bukkit.broadcastMessage("Ilosc polaczen na sek: " + ilosc_polaczen / 5 + "/s");
                ilosc_polaczen = 0;


            }
        }.runTaskTimerAsynchronously(this, 20L, 20 * 5);


    }

    public void downloaddb() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    try {
                        Files.copy(
                                new URL("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-ASN.mmdb").openStream(),
                                Paths.get("./plugins/Anti-vpn/GeoLite2-ASN.mmdb"), REPLACE_EXISTING);
                        Files.copy(
                                new URL("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-Country.mmdb").openStream(),
                                Paths.get("./plugins/Anti-vpn/GeoLite2-Country.mmdb"), REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (NullPointerException ignored) {
                }

            }
        }.runTaskAsynchronously(this);


    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Country_list = this.getConfig().getStringList("Country.list");

    }


    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission(this.getConfig().getString("permission-admin"))) {
            if (args[0].equals("reload")) {
                loaddb();
                reloadConfig();
                sender.sendMessage("Zaktualizowano baze danych, Przeladowano konfig");
            } else if (args[0].equals("list")) {
                sender.sendMessage(String.valueOf(ASN_List));
                sender.sendMessage(String.valueOf(this.getConfig().getStringList("Country.list")));

            } else if (args[0].equals("info")) {
                sender.sendMessage(String.valueOf(Bukkit.getPlayer(args[1]).getAddress()));
            } else {
                sender.sendMessage("/avpn reload");

            }
        }

        return false;
    }

    @EventHandler
    public void Onjoin(AsyncPlayerPreLoginEvent e) throws IOException {

        String ip = e.getAddress().getHostAddress();
        ilosc_polaczen++;
        FileConfiguration config = this.getConfig();
        AsyncPlayerPreLoginEvent.Result kick = AsyncPlayerPreLoginEvent.Result.KICK_FULL;

        check(1, e.getAddress().getHostAddress(), e);

        if (CheckORG.check(ip) && config.getBoolean("Organization.enabled")) {
            e.disallow(kick, config.getString("Messages.block-vpn"));
            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bORG (" + ip + ")");
            //punish(ip);


        } else if (CheckCountry.check(ip) && config.getBoolean("Country.enabled")) {
            if(config.getBoolean("Country.only-attack")){
                if(attack){
                    e.disallow(kick, config.getString("Messages.block-country"));
                    Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bkraj (" + ip + ")");
                }
            } else {
                e.disallow(kick, config.getString("Messages.block-country"));
                Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                //punish(ip);
            }

        } else if (CheckMaxIP.check(e.getAddress().getHostName(), this) && config.getBoolean("max-join-per-ip.enabled")) {
            if(config.getBoolean("max-join-per-ip.only-attack")){
                if(attack){
                    e.disallow(kick, config.getString("Messages.block-max-connections"));
                    Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bmax-ip (" + ip + ")");
                }
            } else {
                e.disallow(kick, config.getString("Messages.block-max-connections"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bmax-ip (" + ip + ")");
                //punish(ip);
            }

        } else if (CheckIPblacklist.check(ip) && config.getBoolean("ip-list-block.enabled")) {
            if(config.getBoolean("ip-list-block.only-attack")){
                if(attack){
                    e.disallow(kick, config.getString("Messages.block-ip-list"));
                    Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bproxy-list (" + ip + ")");
                }
            } else {
                e.disallow(kick, config.getString("Messages.block-ip-list"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bproxy-list (" + ip + ")");
                //punish(ip);
            }

            //punish(ip);

        } else if (config.getBoolean("api.enabled")) {
            if (CheckVPNapi.check(ip)) {
                e.disallow(kick, config.getString("Messages.block-vpn"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bAPI (" + ip + ")");
                //punish(ip);
            }
        } else {
            System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");

        }

    }

    public void punish(String ip) {
        List<String> words = this.getConfig().getStringList("Lista");
        words.add(ip);
        this.getConfig().set("Lista", words);

    }


    public void check(int a, String ip, AsyncPlayerPreLoginEvent e) throws IOException{
        AsyncPlayerPreLoginEvent.Result kick = AsyncPlayerPreLoginEvent.Result.KICK_FULL;
        FileConfiguration config = this.getConfig();

        switch(a){
            case 1:
                if (CheckORG.check(ip) && config.getBoolean("Organization.enabled")) {
                    e.disallow(kick, config.getString("Messages.block-vpn"));
                    Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bORG (" + ip + ")");
                    //punish(ip);
                } else {
                    check(2, ip, e);
                }
            case 2:
                if (CheckCountry.check(ip) && config.getBoolean("Country.enabled")) {
                    if(config.getBoolean("Country.only-attack")){
                        if(attack){
                            e.disallow(kick, config.getString("Messages.block-country"));
                            Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bkraj (" + ip + ")");
                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-country"));
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bkraj (" + ip + ")");
                        //punish(ip);
                    }
                } else {
                    check(3, ip, e);
                }
            case 3:
                if (CheckMaxIP.check(e.getAddress().getHostName(), this) && config.getBoolean("max-join-per-ip.enabled")) {
                    if (config.getBoolean("max-join-per-ip.only-attack")) {
                        if (attack) {
                            e.disallow(kick, config.getString("Messages.block-max-connections"));
                            Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                        }
                    } else {
                        e.disallow(kick, config.getString("Messages.block-max-connections"));
                        Bukkit.getLogger().log(Level.INFO, "§4Zablokowano §bmax-ip (" + ip + ")");
                        //punish(ip);
                    }
                } else {
                    check(5, ip, e);
                }



        }






if (CheckMaxIP.check(e.getAddress().getHostName(), this) && config.getBoolean("max-join-per-ip.enabled")) {
            if(config.getBoolean("max-join-per-ip.only-attack")){
                if(attack){
                    e.disallow(kick, config.getString("Messages.block-max-connections"));
                    Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bmax-ip (" + ip + ")");
                }
            } else {
                e.disallow(kick, config.getString("Messages.block-max-connections"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bmax-ip (" + ip + ")");
                //punish(ip);
            }

        } else if (CheckIPblacklist.check(ip) && config.getBoolean("ip-list-block.enabled")) {
            if(config.getBoolean("ip-list-block.only-attack")){
                if(attack){
                    e.disallow(kick, config.getString("Messages.block-ip-list"));
                    Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bproxy-list (" + ip + ")");
                }
            } else {
                e.disallow(kick, config.getString("Messages.block-ip-list"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bproxy-list (" + ip + ")");
                //punish(ip);
            }

            //punish(ip);

        } else if (config.getBoolean("api.enabled")) {
            if (CheckVPNapi.check(ip)) {
                e.disallow(kick, config.getString("Messages.block-vpn"));
                Bukkit.getLogger().log(Level.INFO,"§4Zablokowano §bAPI (" + ip + ")");
                //punish(ip);
            }
        } else {
            System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");

        }

    }

    }


    public void getIpfromApi(String link) {
        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    Scanner s = new Scanner(url.openStream());

                    while (s.hasNextLine()) {
                        listaip.add(s.nextLine().split(":")[0]);
                    }
                    s.close();

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });
    }

    public void getIpfromApi1(String link) {
        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    Scanner s = new Scanner(url.openStream());

                    while (s.hasNextLine()) {
                        String line = s.nextLine();
                        if (!line.contains("#"))
                            listaip.add(line.split("\t")[0]);
                    }
                    s.close();

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });
    }

    public void loaddb() {
        try {
            URL u = new URL("https://raw.githubusercontent.com/koloksk/avpn-asn-black-list/main/list.txt");
            URLConnection conn = u.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                buffer.append(inputLine);
            in.close();
            String t = buffer.toString();
            String[] a = t.split(",");
            ASN_List = new ArrayList<>(Arrays.asList(a));

        } catch (IOException e) {
            return;
        }
        getIpfromApi("https://api.proxyscrape.com/v2/?request=getproxies&protocol=socks4&timeout=10000&country=all");
        getIpfromApi("https://api.proxyscrape.com/v2/?request=getproxies&protocol=socks5&timeout=10000&country=all");
        getIpfromApi("https://www.proxyscan.io/download?type=socks4");
        getIpfromApi("https://www.proxyscan.io/download?type=socks5");
        getIpfromApi("https://raw.githubusercontent.c00om/clarketm/proxy-list/master/proxy-list-raw.txt");
        if (this.getConfig().getBoolean("ipsum.enabled"))
            getIpfromApi1("https://raw.githubusercontent.com/stamparm/ipsum/master/ipsum.txt");





    }


}
