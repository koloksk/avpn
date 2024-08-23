package pl.koloksk.Common.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import java.io.IOException;
import java.net.InetAddress;

public class InfoUtils {

    private static DatabaseReader countryReader;
    private static DatabaseReader orgReader;

    static {
        try {
            countryReader = new DatabaseReader.Builder(StoreData.codatabase).build();
            orgReader = new DatabaseReader.Builder(StoreData.orgdatabase).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InetAddress getInetAddress(String ip) throws IOException {
        return InetAddress.getByName(ip);
    }

    public static String getCountry(String ip) {
        try {
            InetAddress ipAddress = getInetAddress(ip);
            CountryResponse response = countryReader.country(ipAddress);
            Country country = response.getCountry();
            return country.getIsoCode();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCountryName(String ip) {
        try {
            InetAddress ipAddress = getInetAddress(ip);
            CountryResponse response = countryReader.country(ipAddress);
            Country country = response.getCountry();
            return country.getName();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getORG(String ip) {
        try {
            InetAddress ipAddress = getInetAddress(ip);
            AsnResponse response = orgReader.asn(ipAddress);
            return response.getAutonomousSystemOrganization();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getASN(String ip) {
        try {
            InetAddress ipAddress = getInetAddress(ip);
            AsnResponse response = orgReader.asn(ipAddress);
            return response.getAutonomousSystemNumber().toString();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCity(String ip) {
        try {
            InetAddress ipAddress = getInetAddress(ip);
            CityResponse response = orgReader.city(ipAddress);
            return response.getCity().getName();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
