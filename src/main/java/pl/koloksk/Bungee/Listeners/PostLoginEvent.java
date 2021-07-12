package pl.koloksk.Bungee.Listeners;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

import static pl.koloksk.Common.Check.Check;
import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class PostLoginEvent implements Listener {
    @EventHandler
    public void onPostLogin(net.md_5.bungee.api.event.PostLoginEvent e) throws IOException {
        String ip = e.getPlayer().getAddress().getHostName();
        String nick = e.getPlayer().getName();
        ilosc_polaczen++;
        if(StoreData.attack) {
            StoreData.AttackJoin.put(nick, ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }
        if(Check(ip, nick )) {
            e.getPlayer().disconnect("VPN is not allowed");
        }
    }
}
