package pl.koloksk.Common.utils;

import java.util.List;

public class Settings {
    public static String permissions_admin;
    public static String permissions_bypass;

    public static String Messages_country;
    public static String Messages_vpn;
    public static String Messages_maxip;
    public static String Messages_nick;


    public static Boolean contry_enabled;
    public static Boolean contry_attack;
    public static Boolean contry_whitelist;
    public static List<String> contry_list;

    public static Boolean asn_enabled;
    public static Boolean asn_attack;

    public static Boolean maxip_enabled;
    public static int maxip_limit;

    public static Boolean iplist_enabled;
    public static Boolean iplist_attack;

    public static Boolean api_enabled;

    public static Boolean blocknick_enabled;
    public static List<String> blocknick_list;

    public static int detect_minjps;

    public static boolean integration_authme_enabled;
    public static String integration_authme_kick;

    public static boolean integration_discord_enabled;
    public static String integration_discord_url;

    public static boolean debug;


}
