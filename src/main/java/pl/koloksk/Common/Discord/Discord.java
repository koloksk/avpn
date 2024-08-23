package pl.koloksk.Common.Discord;

import java.awt.*;
import java.io.IOException;

public class Discord {
    public static void sendDiscord(String webhooklink, String nick, String ip) {
        DiscordWebhook webhook = new DiscordWebhook(webhooklink);
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
