package pl.koloksk.Common.checks;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import pl.koloksk.Bukkit.Main;
import pl.koloksk.Common.utils.StoreData;

import java.io.IOException;
import java.net.InetAddress;

public class CheckORG {
    public static boolean check(String ip) throws IOException {

        try (DatabaseReader reader = new DatabaseReader.Builder(Main.orgdatabase).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);

            AsnResponse response = reader.asn(ipAddress);
            String out = response.getAutonomousSystemNumber().toString();
            return StoreData.ASN_List.contains(out);
        } catch (GeoIp2Exception e) {
            return false;
        }


    }
}
