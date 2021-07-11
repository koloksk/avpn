package pl.koloksk.Bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.koloksk.Common.utils.InfoUtils;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

public class Commands implements CommandExecutor {
    public static Main plugin;

    public Commands(Main pl) {
        plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission(Settings.permissions_admin)) {
            if (args.length < 1) {
                sender.sendMessage("\n\u00a7e\u00a7l[\u00a76\u00a7lAVPN\u00a7e\u00a7l] \u00a7r");
                sender.sendMessage(" \u00a76\u00bb \u00a7bCommands: \n");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn reload");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn list - list blocked asn");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn info <player> - advanced info about player");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn transfer");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn checkip");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn notif");
                sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn reload");
                sender.sendMessage("");
                sender.sendMessage(" \u00a77\u00bb \u00a78AVPN");
            } else if (args[0].equals("reload")) {
                LoadDB.loaddb();
                plugin.reloadConfig();
                sender.sendMessage("Zaktualizowano baze danych, Przeladowano konfig");
            } else if (args[0].equals("list")) {
                sender.sendMessage(String.valueOf(StoreData.ASN_List));
                sender.sendMessage(String.valueOf(Settings.contry_list));

            } else if (args[0].equals("info") && !args[1].isEmpty()) {
                String ip = Bukkit.getPlayer(args[1]).getAddress().getHostString();
                sender.sendMessage("ip:" + ip);
                sender.sendMessage("Country: " + InfoUtils.getCountry(ip));
                sender.sendMessage("Country Name: " + InfoUtils.getCountryName(ip));
                sender.sendMessage("City: " + InfoUtils.getCity(ip));
                sender.sendMessage("ORG: " + InfoUtils.getORG(ip));

            }
            else if (args[0].equals("stats")) {
                sender.sendMessage("Zablokowane ip: " + StoreData.blocked);
                //sender.sendMessage(String.valueOf(StoreData.AttackJoin));
            }

            return false;
        }
        return false;
    }
}
