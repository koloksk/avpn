package pl.koloksk.Common.checks;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import pl.koloksk.Bukkit.Main;

import java.io.IOException;
import java.net.InetAddress;

public class CheckCountry {
    public static boolean check(String ip) throws IOException {

        try (DatabaseReader reader = new DatabaseReader.Builder(Main.codatabase).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);

            CountryResponse response = reader.country(ipAddress);
            Country country = response.getCountry();

            String co = country.getIsoCode();
            return !Main.Country_list.contains(co);
        } catch (GeoIp2Exception e) {
            return false;
        }


    }
}
