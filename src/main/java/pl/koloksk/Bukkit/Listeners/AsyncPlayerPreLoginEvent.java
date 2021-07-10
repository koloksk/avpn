package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

import static pl.koloksk.Bukkit.Check.Check;
import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class AsyncPlayerPreLoginEvent implements Listener {
    AuthMeApi authmeApi = AuthMeApi.getInstance();

    @EventHandler
    public void Onjoin(org.bukkit.event.player.AsyncPlayerPreLoginEvent e) throws IOException {
        String ip = e.getAddress().getHostAddress();
        ilosc_polaczen++;
        if(Main.attack) {
            StoreData.AttackJoin.put(e.getName(), ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }
        if(Main.attack && !authmeApi.isRegistered(e.getName())){
            e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "aa");
        }
        Check(1, ip, e);
    }

}
