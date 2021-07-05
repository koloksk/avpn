package pl.koloksk.modules;

import pl.koloksk.Main;

public class CheckIPblacklist {
    public static boolean check(String ip){
        return Main.listaip.contains(ip);

    }
}
