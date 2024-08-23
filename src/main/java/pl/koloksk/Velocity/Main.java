package pl.koloksk.Velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.slf4j.Logger;
import pl.koloksk.Common.utils.DB.LoadDB;
import pl.koloksk.Common.utils.StoreData;
import pl.koloksk.Velocity.Listeners.PreLoginEvent;
import xyz.kyngs.librelogin.api.LibreLoginPlugin;
import xyz.kyngs.librelogin.api.provider.LibreLoginProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static pl.koloksk.Common.utils.Settings.*;

@Plugin(id = "avpn", name = "Anti VPN", version = "0.1.0-SNAPSHOT", authors = {"koloksk"})
public class Main {

    private static ProxyServer instance;
    private static Main plugin;
    private static YamlDocument config; // Changed to YamlDocument
    private static Path dataDirectory;  // Changed to static
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Main(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        Main.dataDirectory = dataDirectory;  // Changed to static
        StoreData.orgdatabase = new File(dataDirectory.toFile(), "GeoLite2-ASN.mmdb");
        StoreData.codatabase = new File(dataDirectory.toFile(), "GeoLite2-Country.mmdb");
        instance = server;
        plugin = this;

        if (!dataDirectory.toFile().exists()) {
            dataDirectory.toFile().mkdirs();
        }

//        if(server.getPluginManager().isLoaded("librelogin")) {
//            LibreLoginPlugin api = ((LibreLoginProvider<Player, RegisteredServer>) server.getPluginManager().getPlugin("librelogin").orElseThrow().getInstance().orElseThrow()).getLibreLogin();
//            api.getDatabaseProvider().getByName("").isRegistered();
//        }
        loadConfig();
        downloadExternalDB();
    }

    public static ProxyServer getInstance() {
        return instance;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void loadConfig() {
        File configFile = new File(dataDirectory.toFile(), "config.yml");

        try {
            config = YamlDocument.create(configFile, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("config.yml")));
        } catch (IOException e) {
            plugin.logger.error("Could not load config file", e);
        }

        settings();
        LoadDB.loaddb();
    }
    public static void downloadExternalDB() {
        LoadDB.downloaddb();
    }


    public static void settings() {
        permissions_admin = config.getString("permissions.admin");
        permissions_bypass = config.getString("permissions.bypass");

        Messages_country = config.getString("messages.country");
        Messages_vpn = config.getString("messages.vpn");
        Messages_maxip = config.getString("messages.max-connections");
        Messages_nick = config.getString("messages.nick");

        contry_enabled = config.getBoolean("country.enabled");
        contry_attack = config.getBoolean("country.only-attack");
        contry_whitelist = config.getBoolean("country.whitelist");
        contry_list = config.getStringList("country.list");

        asn_enabled = config.getBoolean("asn.enabled");
        asn_attack = config.getBoolean("asn.only-attack");

        maxip_enabled = config.getBoolean("max-join-per-ip.enabled");
        maxip_limit = config.getInt("max-join-per-ip.limit");

        iplist_enabled = config.getBoolean("ip-list.enabled");
        iplist_attack = config.getBoolean("ip-list.only-attack");

        api_enabled = config.getBoolean("api.enabled");

        blocknick_enabled = config.getBoolean("block_nick.enabled");
        blocknick_list = config.getStringList("block_nick.list");

        detect_minjps = config.getInt("detect_attack.min-jps");

        integration_authme_enabled = config.getBoolean("integrations.authme.enabled");
        integration_authme_kick = config.getString("integrations.authme.kick");

        integration_discord_enabled = config.getBoolean("integrations.discord.enabled");
        integration_discord_url = config.getString("integrations.discord.webhook-url");

        debug = config.getBoolean("Debug");
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PreLoginEvent());
        // Register command
        server.getCommandManager().register(server.getCommandManager().metaBuilder("avpn").build(), new Commands());
    }
}
