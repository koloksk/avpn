package pl.koloksk.Velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import pl.koloksk.Common.Config.ConfigManager;
import pl.koloksk.Common.utils.DB.LoadDB;
import pl.koloksk.Common.utils.StoreData;
import pl.koloksk.Velocity.Listeners.PreLoginEvent;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "avpn", name = "Anti VPN", version = "0.1.0-SNAPSHOT", authors = {"koloksk"})
public class Main {

    private static ProxyServer instance;
    private static Main plugin;
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Main(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        StoreData.orgdatabase = new File(dataDirectory.toFile(), "GeoLite2-ASN.mmdb");
        StoreData.codatabase = new File(dataDirectory.toFile(), "GeoLite2-Country.mmdb");
        instance = server;
        plugin = this;

        ConfigManager.loadConfig();
        LoadDB.loaddb();

        downloadExternalDB();
    }

    public static ProxyServer getInstance() {
        return instance;
    }

    public static Main getPlugin() {
        return plugin;
    }


    public static void downloadExternalDB() {
        LoadDB.downloaddb();
    }



    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PreLoginEvent());
        // Register command
        server.getCommandManager().register(server.getCommandManager().metaBuilder("avpn").build(), new Commands());
    }
}
