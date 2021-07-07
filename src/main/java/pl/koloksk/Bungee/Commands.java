package pl.koloksk.Bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import pl.koloksk.Common.utils.StoreData;

public class Commands extends Command {
    public Commands(String name, Plugin pl) {
        super(name);
    }

    public void execute(CommandSender sender, String[] args) {
            if (args[0].equals("reload")) {
                LoadDB.loaddb(Main.getinstance(), Main.getPlugin());
                sender.sendMessage(new TextComponent("Zaktualizowano baze danych, Przeladowano konfig"));
            } else if (args[0].equals("list")) {
                sender.sendMessage(new TextComponent(String.valueOf(StoreData.ASN_List)));
                //sender.sendMessage(new TextComponent(String.valueOf(Main.getPlugin().getConfig().getStringList("Country.list"))));

            } else if (args[0].equals("info")) {
                sender.sendMessage(new TextComponent(String.valueOf(Main.getinstance().getPlayer(args[1]).getAddress())));
            } else {
                sender.sendMessage(new TextComponent("/avpn reload"));

            }



    }
}
