package pl.koloksk.Common.Detection.checks;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import pl.koloksk.Common.Config.Config;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class CheckCountry {
    public static boolean check(String ip, File countryFile) throws IOException {

        try (DatabaseReader reader = new DatabaseReader.Builder(countryFile).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse response = reader.country(ipAddress);
            Country country = response.getCountry();
            String co = country.getIsoCode();
            //Bukkit.getLogger().info(ip +"/"+co);
            if(Config.contry_whitelist){
                return !Config.contry_list.contains(co);
            } else {
                return Config.contry_list.contains(co);

            }
        } catch (GeoIp2Exception e) {
            return false;
        }


    }
}
