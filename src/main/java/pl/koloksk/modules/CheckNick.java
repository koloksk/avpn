package pl.koloksk.modules;

import pl.koloksk.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckNick {
    public static boolean check(String nick, Main plugin){
            for(String regex: plugin.getConfig().getStringList("block_nick.list")) {
                Pattern pattern = Pattern.compile(nick, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(regex);
                boolean matchFound = matcher.find();
                if (matchFound) {
                    return true;
                }
            }

            return false;
    }
}
