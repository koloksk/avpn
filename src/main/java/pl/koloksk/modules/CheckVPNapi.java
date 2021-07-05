package pl.koloksk.modules;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CheckVPNapi {
    public static boolean check(String ip) {
        if(ip.equals("127.0.0.1"))
            return false;
        try {
            StringBuilder jsonS = new StringBuilder();
            URL url = new URL("https://proxycheck.io/v2/" + ip + "?vpn=1");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonS.append(inputLine);
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
            Bukkit.getLogger().info(jsonObject.toString());

            JsonObject asn = jsonObject.get(ip).getAsJsonObject();
            String proxy = asn.get("proxy").getAsString();
            Bukkit.getLogger().info(proxy);

            in.close();
            return proxy.equals("yes");
        } catch (Exception e) {
            return false;
        }

    }
}
