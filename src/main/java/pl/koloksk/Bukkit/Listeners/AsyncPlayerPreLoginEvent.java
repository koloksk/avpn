package pl.koloksk.Bukkit.Listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.CheckManager;
import pl.koloksk.Common.CheckResults;
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
        sprawdz.Check();
        if(sprawdz.getResult() != null && sprawdz.getResult() != CheckResults.ALLOW){
            StoreData.blocked++;
            StoreData.ilosc_blokad++;
            if(sprawdz.getResult() == CheckResults.COUNTRY)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_country);
            if(sprawdz.getResult() == CheckResults.VPN)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_vpn);
            if(sprawdz.getResult() == CheckResults.NICK)
                e.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Settings.Messages_nick);
            if(Settings.integration_discord_enabled && !StoreData.attack) {
                Discord.sendDiscord(nick, ip);
            }




/*            if(!Main.mcver.startsWith("1.8")) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.hasPermission(Settings.permissions_admin) || p.isOp()) {
                        String message = "§6§lZablokowano " + ip;
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    }
                }
            }*/


        } else {
            Bukkit.broadcastMessage("EVENT BYL KURWA NULL"+ sprawdz.getResult());

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
