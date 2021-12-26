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
                result = CheckResults.NICK;

        } else if (CheckORG.check(ip) && Settings.asn_enabled) {
                result = CheckResults.VPN;

        } else if (CheckCountry.check(ip) && Settings.contry_enabled) {
                result = CheckResults.COUNTRY;

        } else if (CheckMaxIP.check(ip) && Settings.maxip_enabled) {
                result = CheckResults.MAXCONNECTIONS;

        } else if (CheckIPblacklist.check(ip) && Settings.iplist_enabled) {
                result = CheckResults.VPN;

        } else if (Settings.api_enabled) {
            if (CheckIpIntel.check(ip)) {
                result = CheckResults.VPN;
            }
            if (CheckVPNapi.check(ip)) {
                result = CheckResults.VPN;

            } else {
                System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
                result = CheckResults.ALLOW;

            }

        } else {
            System.out.println("§2Polaczenie zaakceptowane §b (" + ip + ")");
            result = CheckResults.ALLOW;
        }

    }
}
