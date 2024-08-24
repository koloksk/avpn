package pl.koloksk.Velocity.Listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import net.kyori.adventure.text.Component;
import pl.koloksk.Common.Detection.CheckManager;
import pl.koloksk.Common.Detection.CheckResults;
import pl.koloksk.Common.Discord.Discord;
import pl.koloksk.Common.Config.Config;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

public class PreLoginEvent {


    @Subscribe(order = PostOrder.FIRST)
    public void onPreLoginEvent(com.velocitypowered.api.event.connection.PreLoginEvent e) throws IOException {

        String ip = e.getConnection().getRemoteAddress().getHostName();
        String nick = e.getUsername();
        //ilosc_polaczen++;
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
                e.setResult(com.velocitypowered.api.event.connection.PreLoginEvent.PreLoginComponentResult.denied(Component.text(Config.Messages_country)));
            if(sprawdz.getResult() == CheckResults.VPN)
                e.setResult(com.velocitypowered.api.event.connection.PreLoginEvent.PreLoginComponentResult.denied(Component.text(Config.Messages_vpn)));
            if(sprawdz.getResult() == CheckResults.NICK)
                e.setResult(com.velocitypowered.api.event.connection.PreLoginEvent.PreLoginComponentResult.denied(Component.text(Config.Messages_nick)));
            if(Config.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(Config.integration_discord_url, nick, ip);
            }
        }
    }

}
