package pl.koloksk.Common.Detection.checks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CheckIpIntel {

    private static final String API_URL = "http://check.getipintel.net/check.php";
    private static final String CONTACT_EMAIL = "sebastiankwaczala4@gmail.com";
    private static final double THRESHOLD = 0.98;

    public static boolean check(String ip) {
        BufferedReader in = null;
        try {
            StringBuilder jsonS = new StringBuilder();
            URL url = new URL(API_URL + "?ip=" + ip + "&format=json&contact=" + CONTACT_EMAIL);
            URLConnection conn = url.openConnection();
            conn.connect();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonS.append(inputLine);
            }

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);

            double score = jsonObject.get("result").getAsDouble();
            return score >= THRESHOLD;
        } catch (Exception e) {
            // Możesz dodać logowanie tutaj
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    // Ignorujemy wyjątek zamknięcia
                }
            }
        }
    }
}
