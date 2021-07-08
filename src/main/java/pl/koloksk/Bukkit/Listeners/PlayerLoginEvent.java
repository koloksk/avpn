package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.koloksk.Bukkit.Main;

public class PlayerLoginEvent implements Listener {
    @EventHandler
    public void ple(PlayerJoinEvent e){
        AuthMeApi authmeApi = AuthMeApi.getInstance();
        if(Main.attack && !authmeApi.isRegistered(e.getPlayer().getName())){
            e.getPlayer().kickPlayer("bay");
        }
    }
}
