package pl.koloksk.Common.utils.DB;

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

    public static void loaddb() {
        loadAsnList("https://raw.githubusercontent.com/koloksk/avpn-asn-black-list/main/list.txt");

        // Listy linków do pobrania danych
        String[] proxyLinks = {
//                "https://www.proxyscan.io/download?type=socks4",
//                "https://www.proxyscan.io/download?type=socks5",
                "https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list-raw.txt",
                "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks4.txt",
                "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt",
                "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/http.txt",
                "https://raw.githubusercontent.com/elliottophellia/proxylist/master/results/mix_checked.txt",
                "https://raw.githubusercontent.com/zenjahid/FreeProxy4u/master/socks5.txt",
                "https://raw.githubusercontent.com/zenjahid/FreeProxy4u/master/socks4.txt",
                "https://raw.githubusercontent.com/zenjahid/FreeProxy4u/master/http.txt",
                "https://raw.githubusercontent.com/koloksk/avpn-asn-black-list/main/blacklist.txt"
        };

        // Pobierz dane z każdego linku
        for (String link : proxyLinks) {
            getIpfromApi(link, false);
        }

        getIpfromApi("https://raw.githubusercontent.com/stamparm/ipsum/master/ipsum.txt", true);
        // getIpfromApi("https://plugins.maner`.fr/MBanProxyVPN/badip.txt", true);
    }

    private static void loadAsnList(String url) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder buffer = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    buffer.append(inputLine);
                }
                StoreData.ASN_List = new ArrayList<>(Arrays.asList(buffer.toString().split(",")));
            }
        } catch (IOException e) {
            // Obsługa błędu - w zależności od wymagań, można dodać logowanie lub inne działanie
            System.err.println("Failed to load ASN list: " + e.getMessage());
        }
    }

    public static void getIpfromApi(String link, boolean filterLines) {
//        Runnable task = () -> {
            try {
                URL url = new URL(link);
                Scanner s = new Scanner(url.openStream());

                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    if (!filterLines || !line.contains("#")) {
                        StoreData.listaip.add(line.split(filterLines ? "\t" : ":")[0]);
                    }
                }
                s.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public static void downloaddb() {
            try {
                System.out.println("\u001B[34m" + "[Avpn] Pobieranie bazy danych ..." + "\u001B[0m");
                System.out.println(StoreData.orgdatabase.getPath());
                downloadFile("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-ASN.mmdb", StoreData.orgdatabase.getPath());
                downloadFile("https://github.com/koloksk/avpn-asn-black-list/raw/main/GeoLite2-Country.mmdb", StoreData.codatabase.getPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    private static void downloadFile(String url, String outputPath) throws IOException {
        Files.copy(new URL(url).openStream(), Paths.get(outputPath), REPLACE_EXISTING);
    }
}
