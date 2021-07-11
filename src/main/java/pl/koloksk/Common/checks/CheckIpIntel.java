package pl.koloksk.Common.checks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CheckIpIntel {
    public static boolean check(String ip) {
        if(ip.equals("127.0.0.1"))
            return false;
        try {
            StringBuilder jsonS = new StringBuilder();
            URL url = new URL("http://check.getipintel.net/check.php?ip=" + ip + "&format=json&contact=sebastiankwaczala4@gmail.com");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonS.append(inputLine);
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
            //Bukkit.getLogger().info(jsonObject.toString());

            int score = jsonObject.get("result").getAsInt();

            in.close();
            return score >= 9.8;
        } catch (Exception e) {
            return false;
        }
    }
}
