package pl.koloksk.Common;

import pl.koloksk.Common.checks.*;
import pl.koloksk.Common.utils.Settings;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;

public class CheckManager {
    public CheckManager(String ip, String name){
    this.name = name;
    this.ip = ip;

    }
    public CheckResults result;
    public String ip;
    public String name;

    public CheckResults getResult(){
        return result;
    }



    public void Check() throws IOException {
        if(ip.equals("127.0.0.1"))
            result = null;

        if (CheckNick.check(name) && Settings.blocknick_enabled) {
            //InfoUtils.blockedInfo(ip, "NICK");
            result = CheckResults.NICK;

            //punish(ip);
        } else if (CheckORG.check(ip) && Settings.asn_enabled) {
            if (Settings.asn_attack && StoreData.attack) {
                //InfoUtils.blockedInfo(ip, "ASN");
                result = CheckResults.VPN;
            } else {
                //InfoUtils.blockedInfo(ip, "ASN");
                result = CheckResults.VPN;

            }
            //punish(ip);
        } else if (CheckCountry.check(ip) && Settings.contry_enabled) {
            if (Settings.contry_attack && StoreData.attack) {
                //InfoUtils.blockedInfo(ip, "Country");
                result = CheckResults.COUNTRY;
            } else {
                //InfoUtils.blockedInfo(ip, "Country");
                result = CheckResults.COUNTRY;

            }
        } else if (CheckMaxIP.check(ip) && Settings.maxip_enabled) {
            //InfoUtils.blockedInfo(ip, "MAX-IP");

            result = CheckResults.VPN;
        } else if (CheckIPblacklist.check(ip) && Settings.iplist_enabled) {
            if (Settings.iplist_attack && StoreData.attack) {
                //InfoUtils.blockedInfo(ip, "BLACKLIST");
                result = CheckResults.VPN;
            } else {
                //InfoUtils.blockedInfo(ip, "BLACKLIST");
                result = CheckResults.VPN;

            }
        } else if (Settings.api_enabled) {
            if (CheckIpIntel.check(ip)) {
                //InfoUtils.blockedInfo(ip, "API-1");
                result = CheckResults.VPN;

            }
            if (CheckVPNapi.check(ip)) {

                //InfoUtils.blockedInfo(ip, "API-2");
                //punish(ip);
                result = CheckResults.VPN;

            } else {
                System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
                result = null;

            }

        } else {
            System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
            result = null;
        }

    }
}
