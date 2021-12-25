package pl.koloksk.Common.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import org.bukkit.Bukkit;
import pl.koloksk.Bukkit.Main;

import java.io.IOException;
import java.net.InetAddress;

public class InfoUtils {
    public static String getCountry(String ip) {
        try (
                DatabaseReader reader = new DatabaseReader.Builder(Main.codatabase).build()) {
            InetAddress ipAddress = InetAddress.getByName(ip);

            CountryResponse response = reader.country(ipAddress);
            Country country = response.getCountry();

            return country.getIsoCode();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getCountryName(String ip) {
        try (
                DatabaseReader reader = new DatabaseReader.Builder(Main.codatabase).build()) {
            InetAddress ipAddress = InetAddress.getByName(ip);

            CountryResponse response = reader.country(ipAddress);
            Country country = response.getCountry();

            return country.getName();
        }catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static String getORG(String ip){

        try (DatabaseReader reader = new DatabaseReader.Builder(Main.orgdatabase).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);

            AsnResponse response = reader.asn(ipAddress);
            return response.getAutonomousSystemOrganization();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getASN(String ip){

        try (DatabaseReader reader = new DatabaseReader.Builder(Main.orgdatabase).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);

            AsnResponse response = reader.asn(ipAddress);
            return response.getAutonomousSystemNumber().toString();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getCity(String ip){

        try (DatabaseReader reader = new DatabaseReader.Builder(Main.orgdatabase).build()) {

            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse response = reader.city(ipAddress);
            return response.getCity().getName();
        } catch (GeoIp2Exception | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
