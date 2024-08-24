package pl.koloksk.Velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import pl.koloksk.Common.Config.ConfigManager;
import pl.koloksk.Common.utils.InfoUtils;
import pl.koloksk.Common.Config.Config;
import pl.koloksk.Common.utils.StoreData;

import java.util.Optional;

public class Commands implements SimpleCommand {

    @Override
    public void execute(final Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if (sender.hasPermission(Config.permissions_admin)) {
            if (args.length < 1) {
                sendHelpMessage(sender);
            } else {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        ConfigManager.reload();
                        sender.sendMessage(Component.text("Zaktualizowano bazę danych, Przeładowano konfig").color(NamedTextColor.GREEN));
                        break;

                    case "list":
                        sender.sendMessage(Component.text("ASN List: " + StoreData.ASN_List));
                        sender.sendMessage(Component.text("Country List: " + Config.contry_list));
                        break;

                    case "info":
                        if (args.length > 1) {
                            Optional<Player> target = Main.getInstance().getPlayer(args[1]);
                            if (target.isPresent()) {
                                String ip = target.get().getRemoteAddress().getHostString();
                                sender.sendMessage(Component.text("UUID: " + target.get().getUniqueId()));
                                sender.sendMessage(Component.text("IP: " + ip));
                                sender.sendMessage(Component.text("Country: " + InfoUtils.getCountry(ip)));
                                sender.sendMessage(Component.text("Country Name: " + InfoUtils.getCountryName(ip)));
                                sender.sendMessage(Component.text("City: " + InfoUtils.getCity(ip)));
                                sender.sendMessage(Component.text("ORG: " + InfoUtils.getORG(ip)));
                            } else {
                                sender.sendMessage(Component.text("Gracz nie jest online").color(NamedTextColor.RED));
                            }
                        } else {
                            sender.sendMessage(Component.text("Użyj: /avpn info <player>").color(NamedTextColor.RED));
                        }
                        break;

                    case "stats":
                        sender.sendMessage(Component.text("Zablokowane IP: " + StoreData.blocked));
                        break;

                    case "modules":
                        sender.sendMessage(Component.text("Api Check: " + Config.api_enabled));
                        sender.sendMessage(Component.text("ASN Check: " + Config.asn_enabled));
                        sender.sendMessage(Component.text("Nick Check: " + Config.blocknick_enabled));
                        sender.sendMessage(Component.text("Country Check: " + Config.contry_enabled));
                        sender.sendMessage(Component.text("IP BlackList Check: " + Config.iplist_enabled));
                        sender.sendMessage(Component.text("Max Conn Check: " + Config.maxip_enabled));
                        break;
                    case "off":
                        StoreData.enabled = false;
                        sender.sendMessage(Component.text("State: " + StoreData.enabled));
                        break;
                    case "on":
                        StoreData.enabled = true;
                        sender.sendMessage(Component.text("State: " + StoreData.enabled));
                        break;

                    default:
                        sendHelpMessage(sender);
                        break;
                }
            }
        } else {
            sender.sendMessage(Component.text("Nie masz uprawnień do używania tej komendy!").color(NamedTextColor.RED));
        }
    }

    private void sendHelpMessage(CommandSource sender) {
        sender.sendMessage(Component.text("\n§e§l[§6§lAVPN§e§l] §r"));
        sender.sendMessage(Component.text(" §6» §bCommands: \n"));
        sender.sendMessage(Component.text(" §c• §e/avpn reload - reload config"));
        sender.sendMessage(Component.text(" §c• §e/avpn list - list blocked ASN"));
        sender.sendMessage(Component.text(" §c• §e/avpn info <player> - advanced info about player"));
        sender.sendMessage(Component.text(" §c• §e/avpn modules - modules status"));
        sender.sendMessage(Component.text(" §c• §e/avpn checkip"));
        sender.sendMessage(Component.text(" §c• §e/avpn off/on"));
        sender.sendMessage(Component.text(" §c• §e/avpn reload"));
        sender.sendMessage(Component.text(""));
        sender.sendMessage(Component.text(" §7» §8AVPN"));
    }
}
