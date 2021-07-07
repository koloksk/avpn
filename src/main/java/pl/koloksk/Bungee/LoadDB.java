package pl.koloksk.Bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.koloksk.Common.utils.StoreData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LoadDB {

    public static ProxyServer instance;
    public static Plugin plugin;
    public static void loaddb(ProxyServer ins, Plugin pl) {
        instance = ins;
        plugin = pl;
        try {
            URL u = new URL("https://raw.githubusercontent.com/koloksk/avpn-asn-black-list/main/list.txt");
            URLConnection conn = u.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                buffer.append(inputLine);
            in.close();
            String t = buffer.toString();
            String[] a = t.split(",");
            StoreData.ASN_List = new ArrayList<>(Arrays.asList(a));

        } catch (IOException e) {
            return;
        }
        getIpfromApi("https://api.proxyscrape.com/v2/?request=getproxies&protocol=socks4&timeout=10000&country=all");
        getIpfromApi("https://api.proxyscrape.com/v2/?request=getproxies&protocol=socks5&timeout=10000&country=all");
        getIpfromApi("https://www.proxyscan.io/download?type=socks4");
        getIpfromApi("https://www.proxyscan.io/download?type=socks5");
        getIpfromApi("https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list-raw.txt");
        getIpfromApi1("https://raw.githubusercontent.com/stamparm/ipsum/master/ipsum.txt");

    }
    public static void getIpfromApi(String link) {

        instance.getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    Scanner s = new Scanner(url.openStream());

                    while (s.hasNextLine()) {
                        StoreData.listaip.add(s.nextLine().split(":")[0]);
                    }
                    s.close();

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });
    }

    public static void getIpfromApi1(String link) {
        instance.getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    Scanner s = new Scanner(url.openStream());

                    while (s.hasNextLine()) {
                        String line = s.nextLine();
                        if (!line.contains("#"))
                            StoreData.listaip.add(line.split("\t")[0]);
                    }
                    s.close();

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });
    }
    public static void downloaddb() {
        instance.getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    Files.copy(
                            new URL("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-ASN.mmdb").openStream(),
                            Paths.get("./plugins/Anti-vpn/GeoLite2-ASN.mmdb"), REPLACE_EXISTING);
                    Files.copy(
                            new URL("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-Country.mmdb").openStream(),
                            Paths.get("./plugins/Anti-vpn/GeoLite2-Country.mmdb"), REPLACE_EXISTING);
                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });
    }
}