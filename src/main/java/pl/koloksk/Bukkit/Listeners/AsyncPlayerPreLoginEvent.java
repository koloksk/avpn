package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.koloksk.Common.Discord.Discord;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

import static pl.koloksk.Common.Check.Check;
import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class AsyncPlayerPreLoginEvent implements Listener {
    AuthMeApi authmeApi = AuthMeApi.getInstance();

    @EventHandler
    public void Onjoin(org.bukkit.event.player.AsyncPlayerPreLoginEvent e) throws IOException {

        String ip = e.getAddress().getHostAddress();
        String nick = e.getName();

        ilosc_polaczen++;

        if(StoreData.attack) {
            StoreData.AttackJoin.put(nick, ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }

        if(StoreData.attack && Settings.integration_authme_enabled && !authmeApi.isRegistered(nick)){
            e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.integration_authme_kick);
        }

        if(Check(ip, nick)) {


            StoreData.blocked++;
            StoreData.ilosc_blokad++;
            e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "VPN IS NOT ALLOWED");
            if(Settings.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(nick, ip);
            }


        }
    }

}
