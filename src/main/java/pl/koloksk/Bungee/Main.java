package pl.koloksk.Bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Commands("avpn", this));
        LoadDB.loaddb(getProxy(), this);
        loadConfig();



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
        LoadDB.downloaddb();
    }
    public static ProxyServer getinstance(){
        return instance;
    }
    public static Plugin getPlugin(){
        return plugin;
    }
    public void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StoreData.Country_list = config.getStringList("Country.list");
        StoreData.blockedNicks = config.getStringList("block_nick.list");

    }
}
