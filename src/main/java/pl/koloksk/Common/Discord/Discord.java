package pl.koloksk.Common.Discord;

import pl.koloksk.Bukkit.Main;

import java.awt.*;
import java.io.IOException;

public class Discord {
    public static void sendDiscord(String nick, String ip) {
        DiscordWebhook webhook = new DiscordWebhook(Main.getinstance().getConfig().getString("integrations.discord.webhook-url"));
        webhook.setAvatarUrl("https://i.imgur.com/L14Cp18.png");
        webhook.setUsername("AVPN BOT");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Zablokowano VPN")
                .setDescription("Wykryto vpn u gracza **"+nick+"** ("+ip+")")
                .setColor(Color.RED)
                .setFooter("AVPN", "https://i.imgur.com/L14Cp18.png"));
        try {
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            return;
        }
// Change appearance of webhook message
    }

}
