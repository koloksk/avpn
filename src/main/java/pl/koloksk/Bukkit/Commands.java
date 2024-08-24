package pl.koloksk.Bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.koloksk.Common.Config.ConfigManager;
import pl.koloksk.Common.Detection.CheckManager;
import pl.koloksk.Common.Detection.CheckResults;
import pl.koloksk.Common.utils.InfoUtils;
import pl.koloksk.Common.Config.Config;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

public class Commands implements CommandExecutor {
    private final Main plugin;

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Config.permissions_admin)) {
            sender.sendMessage("You do not have permission to execute this command.");
            return false;
        }

        if (args.length < 1) {
            displayHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                ConfigManager.reload();
                sender.sendMessage("Zaktualizowano bazę danych, Przeładowano konfigurację.");
                break;

            case "list":
                sender.sendMessage(StoreData.ASN_List.toString());
                sender.sendMessage(Config.contry_list.toString());
                sender.sendMessage(StoreData.listaip.toString());
                break;

            case "info":
                if (args.length > 1) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) != null) {
                        String ip = Bukkit.getPlayer(playerName).getAddress().getHostString();
                        sender.sendMessage("UUID: " + Bukkit.getPlayer(playerName).getUniqueId());
                        sender.sendMessage("IP: " + ip);
                        sender.sendMessage("Country: " + InfoUtils.getCountry(ip));
                        sender.sendMessage("Country Name: " + InfoUtils.getCountryName(ip));
                        sender.sendMessage("City: " + InfoUtils.getCity(ip));
                        sender.sendMessage("ORG: " + InfoUtils.getORG(ip));
                    } else {
                        sender.sendMessage("Player not found.");
                    }
                } else {
                    sender.sendMessage("Please specify a player.");
                }
                break;
            case "checkip":
                if (args.length > 1) {
                    String ip = args[1];
                    CheckManager sprawdz = new CheckManager(ip, " ");
                    CheckResults result = null;
                    try {
                        result = sprawdz.Check();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sender.sendMessage(result.name());
                } else {
                    sender.sendMessage("Please specify a player.");
                }
                break;

            case "stats":
                sender.sendMessage("Zablokowane IP: " + StoreData.blocked);
                break;

            case "modules":
                displayModulesStatus(sender);
                break;

            default:
                sender.sendMessage("Unknown command. Use /avpn for help.");
                break;
        }

        return true;
    }

    private void displayHelp(CommandSender sender) {
        sender.sendMessage("\n\u00a7e\u00a7l[\u00a76\u00a7lAVPN\u00a7e\u00a7l] \u00a7r");
        sender.sendMessage(" \u00a76\u00bb \u00a7bCommands: \n");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn reload");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn list - list blocked ASN");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn info <player> - advanced info about player");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn modules - modules status");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn checkip <ip> - check ip ");
        sender.sendMessage(" \u00a7c\u2022 \u00a7e/avpn stats");
        sender.sendMessage("");
        sender.sendMessage(" \u00a77\u00bb \u00a78AVPN");
    }

    private void displayModulesStatus(CommandSender sender) {
        sender.sendMessage("Api Check: " + Config.api_enabled);
        sender.sendMessage("ASN Check: " + Config.asn_enabled);
        sender.sendMessage("Nick Check: " + Config.blocknick_enabled);
        sender.sendMessage("Country Check: " + Config.contry_enabled);
        sender.sendMessage("IP BlackList Check: " + Config.iplist_enabled);
        sender.sendMessage("Max Conn Check: " + Config.maxip_enabled);
    }
}
