package pl.koloksk.Common.Detection.checks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CheckVPNapi {

    private static final String API_URL = "https://proxycheck.io/v2/";

    public static boolean check(String ip) {
        try {
            String jsonResponse = getApiResponse(ip);
            return parseJsonResponse(jsonResponse, ip);
        } catch (Exception e) {
            return false;
        }
    }

    private static String getApiResponse(String ip) throws Exception {
        StringBuilder jsonS = new StringBuilder();
        URL url = new URL(API_URL + ip + "?vpn=1");
        URLConnection conn = url.openConnection();
        conn.connect();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonS.append(inputLine);
            }
        }
        return jsonS.toString();
    }

    private static boolean parseJsonResponse(String jsonResponse, String ip) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        JsonObject asn = jsonObject.get(ip).getAsJsonObject();
        String proxy = asn.get("proxy").getAsString();

        //Bukkit.getLogger().info(proxy);
        return "yes".equals(proxy);
    }
}
