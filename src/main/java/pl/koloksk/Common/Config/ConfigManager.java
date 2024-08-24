package pl.koloksk.Common.Config;

import dev.dejvokep.boostedyaml.YamlDocument;
import pl.koloksk.Common.utils.DB.LoadDB;
import pl.koloksk.Velocity.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static pl.koloksk.Common.Config.Config.*;

public class ConfigManager {
    private static YamlDocument config; // Changed to YamlDocument
    private static Path path = Paths.get("./plugins/avpn");

    public static void loadConfig() {
        if (!path.toFile().exists()) {
            path.toFile().mkdirs();
        }
        File configFile = new File("./plugins/avpn", "config.yml");

        try {
            config = YamlDocument.create(configFile, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("config.yml")));
        } catch (IOException e) {
            System.err.println("Blad ladowania configu: " + e.getMessage());

        }

        load();
//        LoadDB.loaddb();
    }
    public static void reload() {
        loadConfig();
    }
    public static void load() {
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

        blocknick_enabled = config.getBoolean("block-nick.enabled");
        blocknick_list = config.getStringList("block-nick.list");

        detect_minjps = config.getInt("detect-attack.min-jps");

        integration_authme_enabled = config.getBoolean("integrations.authme.enabled");
        integration_authme_kick = config.getString("integrations.authme.kick");

        integration_discord_enabled = config.getBoolean("integrations.discord.enabled");
        integration_discord_url = config.getString("integrations.discord.webhook-url");
        integration_discord_title = config.getString("integrations.discord.title");
        integration_discord_avatar = config.getString("integrations.discord.avatar-url");
        integration_discord_username = config.getString("integrations.discord.usernam");
        integration_discord_description = config.getString("integrations.discord.description");
        integration_discord_footer = config.getString("integrations.discord.footer");

        debug = config.getBoolean("Debug");

        console_filter_enabled = config.getBoolean("console-filter.enabled");
        console_filter_only_attack = config.getBoolean("console-filter.only-attack");
        console_filter_list = config.getStringList("console-filter.filter-list");

    }
}
