package pl.koloksk.Common.Detection;

import pl.koloksk.Common.Detection.checks.*;
import pl.koloksk.Common.Config.Config;
import pl.koloksk.Common.utils.StoreData;

import java.io.File;
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



    public CheckResults Check() throws IOException {
        File orgfile = StoreData.orgdatabase;
        File countryfile = StoreData.codatabase;

        if (!StoreData.enabled)
            return CheckResults.ALLOW;

        if(ip.equals("127.0.0.1"))
            return CheckResults.ALLOW;

        if (CheckNick.check(name) && Config.blocknick_enabled) {
            return CheckResults.NICK;

        }

        if (CheckCountry.check(ip, countryfile) && Config.contry_enabled) {
            return CheckResults.COUNTRY;
        }

        if (CheckORG.check(ip, orgfile) && Config.asn_enabled) {
            return CheckResults.VPN;
        }

//        if (CheckMaxIP.check(ip) && Settings.maxip_enabled) {
//            return CheckResults.MAXCONNECTIONS;
//
//        }

        if (CheckIPblacklist.check(ip) && Config.iplist_enabled) {
            return CheckResults.VPN;

        }

        if (Config.api_enabled) {
            if (CheckIpIntel.check(ip)) {
                return CheckResults.VPN;
            }
            if (CheckVPNapi.check(ip)) {
                return CheckResults.VPN;

            }

        }

        System.out.println("Polaczenie zaakceptowane (" + ip + ")");
        return CheckResults.ALLOW;


    }
}
