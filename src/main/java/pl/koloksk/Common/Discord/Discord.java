package pl.koloksk.Common.Discord;

import pl.koloksk.Common.Config.Config;

import java.awt.*;
import java.io.IOException;

public class Discord {
    public static void sendDiscord(String webhooklink, String nick, String ip) {
        DiscordWebhook webhook = new DiscordWebhook(webhooklink);
        webhook.setAvatarUrl(Config.integration_discord_avatar);
        webhook.setUsername(Config.integration_discord_username);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Config.integration_discord_title)
                .setDescription(Config.integration_discord_description.replaceAll("%playername%",nick).replaceAll("%ip%", ip))
                .setColor(Color.RED)
                .setFooter(Config.integration_discord_footer, Config.integration_discord_avatar));
        try {
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            return;
        }
// Change appearance of webhook message
    }

}
