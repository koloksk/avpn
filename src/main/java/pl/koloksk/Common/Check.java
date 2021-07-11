package pl.koloksk.Common;

import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.checks.*;
import pl.koloksk.Common.utils.InfoUtils;
import pl.koloksk.Common.utils.Settings;

import java.io.IOException;

public class Check {
    public static boolean Check(String ip, String name) throws IOException {

        if (CheckNick.check(name) && Settings.blocknick_enabled) {
            InfoUtils.blockedInfo(ip, "NICK");

            return true;

            //punish(ip);
        } else if (CheckORG.check(ip) && Settings.asn_enabled) {
            if (Settings.asn_attack && Main.attack) {
                InfoUtils.blockedInfo(ip, "ASN");
                return true;
            } else {
                InfoUtils.blockedInfo(ip, "ASN");
                return true;

            }
            //punish(ip);
        } else if (CheckCountry.check(ip) && Settings.contry_enabled) {
            if (Settings.contry_attack && Main.attack) {
                    InfoUtils.blockedInfo(ip, "Country");
                    return true;
            } else {
                InfoUtils.blockedInfo(ip, "Country");
                return true;

            }
        } else if (CheckMaxIP.check(ip) && Settings.maxip_enabled) {
                InfoUtils.blockedInfo(ip, "MAX-IP");

                return true;
        } else if (CheckIPblacklist.check(ip) && Settings.iplist_enabled) {
            if (Settings.iplist_attack && Main.attack) {
                    InfoUtils.blockedInfo(ip, "BLACKLIST");
                    return true;
            } else {
                InfoUtils.blockedInfo(ip, "BLACKLIST");
                return true;

            }
        } else if (Settings.api_enabled) {
            if (CheckIpIntel.check(ip)) {
                InfoUtils.blockedInfo(ip, "API-1");
                return true;

            }
        } else if (Settings.api_enabled) {
            if (CheckVPNapi.check(ip)) {

                InfoUtils.blockedInfo(ip, "API-2");
                //punish(ip);
                return true;

            } else {
                System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
                return false;

            }
        } else {
            System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
            return false;
        }

        return false;

    }


}