package pl.koloksk.Common.checks;

import pl.koloksk.Common.utils.Settings;

public class CheckNick {
    public static boolean check(String nick){
            for(String regex: Settings.blocknick_list) {
                if(nick.contains(regex)){
                    return true;
                }
            }

            return false;
    }
}
