package pl.koloksk.Common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreData {
    public static ArrayList<String> ASN_List;
    public static List<String> listaip = new ArrayList<>();
    public static int ilosc_polaczen = 0;
    public static int ilosc_blokad = 0;

    public static boolean bungeeEnabled = false;


    public static int blocked;

    public static boolean attack = false;
    public static HashMap<String, String> AttackJoin = new HashMap<>();
}
