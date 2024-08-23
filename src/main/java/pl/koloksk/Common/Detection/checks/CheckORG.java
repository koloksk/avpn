package pl.koloksk.Common.Detection.checks;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;

import pl.koloksk.Common.utils.StoreData;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class CheckORG {

    public static boolean check(String ip, File orgfile) throws IOException {
        try (DatabaseReader reader = new DatabaseReader.Builder(orgfile).build()) {
            InetAddress ipAddress = InetAddress.getByName(ip);
            AsnResponse response = getAsnResponse(reader, ipAddress);
            return isAsnInList(response);
        } catch (GeoIp2Exception e) {
            // Możesz dodać logowanie błędu tutaj
            return false;
        }
    }

    private static AsnResponse getAsnResponse(DatabaseReader reader, InetAddress ipAddress) throws GeoIp2Exception, IOException {
        return reader.asn(ipAddress);
    }

    private static boolean isAsnInList(AsnResponse response) {
        String asn = response.getAutonomousSystemNumber().toString();
        return StoreData.ASN_List.contains(asn);
    }
}
