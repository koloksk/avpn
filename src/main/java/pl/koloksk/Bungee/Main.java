package pl.koloksk.Bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.koloksk.Common.utils.LoadDB;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static pl.koloksk.Common.utils.Settings.*;

public class Main extends Plugin {
    public static ProxyServer instance;
    public static Plugin plugin;
    public static Configuration config;
    @Override
    public void onEnable() {
        // You should not put an enable message in your plugin.
        // BungeeCord already does so
        instance = getProxy();
        plugin = this;
        StoreData.bungeeEnabled = true;
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Commands("avpn", this));

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LoadDB.bplugin = plugin;
        LoadDB.binstance = getProxy();

        loadConfig();


    }
    public static ProxyServer getinstance(){
        return instance;
    }
    public static Plugin getPlugin(){
        return plugin;
    }
    public static void loadConfig() {

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        settings();
        LoadDB.loaddb();
        LoadDB.downloaddb();

    }
    public static void settings() {
        permissions_admin = config.getString("permissions.admin");
        permissions_bypass = config.getString("permissions.bypass");

        Messages_country = config.getString("messages.country");
        Messages_vpn = config.getString("messages.vpn");
        Messages_maxip = config.getString("messages.max-connections");
        Messages_iplist = config.getString("messages.iplist");


        contry_enabled = config.getBoolean("country.enabled");
        contry_attack = config.getBoolean("country.only-attack");
        contry_whitelist = config.getBoolean("country.whitelist");
        contry_list = config.getStringList("country.list");

        asn_enabled = config.getBoolean("asn.enabled");
        asn_attack = config.getBoolean("asn.only-attack");;

        maxip_enabled = config.getBoolean("max-join-per-ip.enabled");
        maxip_limit = config.getInt("max-join-per-ip.limit");;

        iplist_enabled = config.getBoolean("ip-list.enabled");
        iplist_attack = config.getBoolean("ip-list.only-attack");


        api_enabled = config.getBoolean("api.enabled");

        blocknick_enabled = config.getBoolean("block_nick.enabled");
        blocknick_list = config.getStringList("block_nick.list");;

        detect_minjps = config.getInt("detect_attack.min-jps");

        integration_authme_enabled = config.getBoolean("integrations.authme.enabled");
        integration_authme_kick = config.getString("integrations.authme.kick");

        integration_discord_enabled = config.getBoolean("integrations.discord.enabled");
        integration_discord_url = config.getString("integrations.discord.webhook-url");

        debug = config.getBoolean("Debug");

    }
}
