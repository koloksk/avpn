/*package pl.koloksk.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CheckMaliciousIP {
    public static boolean check(String ip){
        try {
            URL u = new URL("http://api.blocklist.de/api.php?ip="+ip+"&start=1");
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
            String[] a = t.split("<br />");
            Integer b = Integer.valueOf(a[0].split(": ")[1]);
            if(b >= )
        } catch (IOException e){
            return;
        }

    }
}*/
