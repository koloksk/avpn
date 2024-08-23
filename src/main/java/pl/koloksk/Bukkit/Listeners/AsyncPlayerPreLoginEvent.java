package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.Detection.CheckManager;
import pl.koloksk.Common.Detection.CheckResults;
import pl.koloksk.Common.Discord.Discord;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

public class AsyncPlayerPreLoginEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void Onjoin(org.bukkit.event.player.AsyncPlayerPreLoginEvent e) throws IOException {

        String ip = e.getAddress().getHostAddress();
        String nick = e.getName();

        StoreData.ilosc_polaczen++;

/*        if(StoreData.attack) {
            StoreData.AttackJoin.put(nick, ip);
            //Bukkit.broadcastMessage("Dodano gracza" + e.getName());
        }*/
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
        CheckResults result = sprawdz.Check();

        if(result != null && result != CheckResults.ALLOW){
            StoreData.blocked++;
            StoreData.ilosc_blokad++;
            if(result == CheckResults.COUNTRY)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_country);
            if(result == CheckResults.VPN)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_vpn);
            if(result == CheckResults.NICK)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_nick);
            if(Settings.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(Settings.integration_discord_url, nick, ip);
            }




/*            if(!Main.mcver.startsWith("1.8")) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.hasPermission(Settings.permissions_admin) || p.isOp()) {
                        String message = "§6§lZablokowano " + ip;
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    }
                }
            }*/


        }
//        else {
//            Bukkit.broadcastMessage("EVENT BYL KURWA NULL"+ result);
//
//        }

/*        if(Check(ip, nick)) {





        } else {


            String filename= "asn.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(InfoUtils.getASN(ip)+"\n");//appends the string to the file
            fw.close();
        }*/
    }

}
