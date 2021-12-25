package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.CheckManager;
import pl.koloksk.Common.CheckResults;
import pl.koloksk.Common.Discord.Discord;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

public class AsyncPlayerPreLoginEvent implements Listener {

    @EventHandler
    public void Onjoin(org.bukkit.event.player.AsyncPlayerPreLoginEvent e) throws IOException {





        String ip = e.getAddress().getHostAddress();
        String nick = e.getName();







        StoreData.ilosc_polaczen++;

        if(StoreData.attack) {
            StoreData.AttackJoin.put(nick, ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }
        if(Main.AuthmeStatus) {
            AuthMeApi authmeApi = AuthMeApi.getInstance();
            if (StoreData.attack && Settings.integration_authme_enabled && !authmeApi.isRegistered(nick)) {
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.integration_authme_kick);
            }
        }

/*        if(Bukkit.getPlayer(e.getName()).hasPermission(Settings.permissions_bypass)){
            e.allow();
        }*/


        CheckManager sprawdz = new CheckManager(ip, nick);
        sprawdz.Check();
        if(sprawdz.getResult() != null){
            StoreData.blocked++;
            StoreData.ilosc_blokad++;
            if(sprawdz.getResult() == CheckResults.COUNTRY)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "YOUR COUNTRY IS NOT ALLOWED");
            if(sprawdz.getResult() == CheckResults.VPN)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "VPN IS NOT ALLOWED");
            if(sprawdz.getResult() == CheckResults.NICK)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "YOUR NICK IS NOT ALLOWED");
            if(Settings.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(nick, ip);
            }
        }

/*        if(Check(ip, nick)) {





        } else {


            String filename= "asn.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(InfoUtils.getASN(ip)+"\n");//appends the string to the file
            fw.close();
        }*/
    }

}
