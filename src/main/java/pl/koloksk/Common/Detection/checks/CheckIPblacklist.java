package pl.koloksk.Common.Detection.checks;

import pl.koloksk.Common.utils.StoreData;

public class CheckIPblacklist {
    public static boolean check(String ip){
        return StoreData.listaip.contains(ip);

    }
}
