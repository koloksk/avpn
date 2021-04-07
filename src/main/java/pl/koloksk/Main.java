package pl.koloksk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {
    String[] a;
    String[] b;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getCommand("avpn").setExecutor(this);
        loaddb();
        loadConfig();

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {


    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission(this.getConfig().getString("permission-reload"))) {
            if (args[0].equals("reload")) {
                loaddb();
                reloadConfig();
                sender.sendMessage("Zaktualizowano baze danych, Przeladowano konfig");
            } else {
                sender.sendMessage("/avpn reload");
            }
        }

        return false;
    }
    @EventHandler
    public void Onjoin(AsyncPlayerPreLoginEvent e) throws IOException {
        String ip = e.getAddress().getHostAddress();
        if(CheckOrg(ip)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "");
            Bukkit.getLogger().info("§4Zablokowano §bORG");


        } else {
            if(CheckCountry(ip)){
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "");
                Bukkit.getLogger().info("§4Zablokowano §bkraj");


            }
        }

        }


    public boolean CheckOrg(String ip) throws IOException {

        StringBuilder jsonS = new StringBuilder();
        URL url = new URL("https://get.geojs.io/v1/ip/geo/" + ip + ".json");
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            jsonS.append(inputLine);
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
        String asn = jsonObject.get("organization_name").getAsString();
        //String co = jsonObject.get("country_code").getAsString();
        Bukkit.getLogger().info("§eOrganizacja: §b"+asn);
      ArrayList<String> aList = new ArrayList<>(Arrays.asList(a));

        in.close();
        if (aList.contains(asn)) {
            return true;
        } else {
            return false;
        }



    }
    public boolean CheckCountry(String ip) throws IOException {

        StringBuilder jsonS = new StringBuilder();
        URL url = new URL("https://get.geojs.io/v1/ip/geo/" + ip + ".json");
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            jsonS.append(inputLine);
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
        String co = jsonObject.get("country_code").getAsString();
        Bukkit.getLogger().info("§eKraj: §b"+co);

        in.close();

        return !co.equals("PL");
/*            ArrayList<String> aList =
                    new ArrayList<>(Arrays.asList(a));
            if (aList.contains(asn)) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.getConfig().getString("block-message"));
            }*/




    }

    public boolean checkco(String co) {
        ArrayList<String> bList =
                new ArrayList<>(Arrays.asList(b));
        return bList.contains(co);
    }

    public void loaddb() {
        try {
            intcs();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void intcs() throws IOException {
        URL u = new URL("https://pastebin.com/raw/yFqQgMQi");
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
        a = t.split("\\|");
    }


}
