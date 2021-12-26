package pl.koloksk.Bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.Bukkit;
import pl.koloksk.Common.utils.InfoUtils;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

public class Commands extends Command {
    public Commands(String name, Plugin pl) {
        super(name);
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission(Settings.permissions_admin)) {
            if (args.length < 1) {
                sender.sendMessage("\n\u00a7e\u00a7l[\u00a76\u00a7lAVPN\u00a7e\u00a7l] \u00a7r");
                sender.sendMessage(" \u00a76\u00bb \u00a7bCommands: \n");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn reload - reload config");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn list - list blocked asn");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn info <player> - advanced info about player");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn modules - modules status");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn checkip");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn notif");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn reload");
                sender.sendMessage("");
                sender.sendMessage(" \u00a77\u00bb \u00a78AVPN");
            } else if (args[0].equals("reload")) {
                Main.loadConfig();
                sender.sendMessage("Zaktualizowano baze danych, Przeladowano konfig");
            } else if (args[0].equals("list")) {
                sender.sendMessage(String.valueOf(StoreData.ASN_List));
                sender.sendMessage(String.valueOf(Settings.contry_list));

            } else if (args[0].equals("info") && !args[1].isEmpty()) {
                String ip = Bukkit.getPlayer(args[1]).getAddress().getHostString();
                sender.sendMessage("UUID: "+ Bukkit.getPlayer(args[1]).getUniqueId());
                sender.sendMessage("ip:" + ip);
                sender.sendMessage("Country: " + InfoUtils.getCountry(ip));
                sender.sendMessage("Country Name: " + InfoUtils.getCountryName(ip));
                sender.sendMessage("City: " + InfoUtils.getCity(ip));
                sender.sendMessage("ORG: " + InfoUtils.getORG(ip));

            }
            else if (args[0].equals("stats")) {
                sender.sendMessage("Zablokowane ip: " + StoreData.blocked);
                //sender.sendMessage(String.valueOf(StoreData.AttackJoin));
            } else if (args[0].equals("modules")) {
                sender.sendMessage("Api Check: " + Settings.api_enabled);
                sender.sendMessage("ASN Check: " + Settings.asn_enabled);
                sender.sendMessage("Nick Check: " + Settings.blocknick_enabled);
                sender.sendMessage("Country Check: " + Settings.contry_enabled);
                sender.sendMessage("IP BlackList Check: " + Settings.iplist_enabled);
                sender.sendMessage("Max Conn Check: " + Settings.maxip_enabled);


                //sender.sendMessage(String.valueOf(StoreData.AttackJoin));
            }
        }




    }
}
