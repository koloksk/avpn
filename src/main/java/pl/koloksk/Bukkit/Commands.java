package pl.koloksk.Bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.koloksk.Common.utils.StoreData;

public class Commands implements CommandExecutor {
    public static Main plugin;
    public Commands(Main pl){
        plugin = pl;
    }
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission(plugin.getConfig().getString("permission-admin"))) {
            if (args[0].equals("reload")) {
                LoadDB.loaddb();
                plugin.reloadConfig();
                sender.sendMessage("Zaktualizowano baze danych, Przeladowano konfig");
            } else if (args[0].equals("list")) {
                sender.sendMessage(String.valueOf(StoreData.ASN_List));
                sender.sendMessage(String.valueOf(plugin.getConfig().getStringList("Country.list")));

            } else if (args[0].equals("info")) {
                sender.sendMessage(String.valueOf(Bukkit.getPlayer(args[1]).getAddress()));
            } else {
                sender.sendMessage("/avpn reload");

            }
        }

        return false;
    }
}
