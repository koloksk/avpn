package pl.koloksk.Common.Detection.checks;

import pl.koloksk.Common.Config.Config;

public class CheckNick {
    public static boolean check(String nick){
            for(String regex: Config.blocknick_list) {
                if(nick.contains(regex)){
                    return true;
                }
            }

            return false;
    }
}
