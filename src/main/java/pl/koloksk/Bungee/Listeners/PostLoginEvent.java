package pl.koloksk.Bungee.Listeners;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.koloksk.Common.CheckManager;
import pl.koloksk.Common.CheckResults;
import pl.koloksk.Common.Discord.Discord;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class PostLoginEvent implements Listener {
    @EventHandler
    public void onPostLogin(net.md_5.bungee.api.event.PostLoginEvent e) throws IOException {
        String ip = e.getPlayer().getAddress().getHostName();
        String nick = e.getPlayer().getName();
        ilosc_polaczen++;
/*        if(StoreData.attack) {
            StoreData.AttackJoin.put(nick, ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }*/

        CheckManager sprawdz = new CheckManager(ip, nick);
        sprawdz.Check();
        if(sprawdz.getResult() != null){
            StoreData.blocked++;
            StoreData.ilosc_blokad++;
            if(sprawdz.getResult() == CheckResults.COUNTRY)
                e.getPlayer().disconnect(Settings.Messages_country);
            if(sprawdz.getResult() == CheckResults.VPN)
                e.getPlayer().disconnect(Settings.Messages_vpn);
            if(sprawdz.getResult() == CheckResults.NICK)
                e.getPlayer().disconnect(Settings.Messages_nick);
            if(Settings.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(nick, ip);
            }
        }
    }
}
