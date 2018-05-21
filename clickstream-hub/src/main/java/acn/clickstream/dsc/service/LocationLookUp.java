package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.Location;
import acn.clickstream.dsc.util.Log;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class LocationLookUp {

    @Value("${geolite.database.path}")
    private String dbLocation;

    private DatabaseReader dbReader = null;

    @PostConstruct
    private void init() throws IOException {
        ClassPathResource res = new ClassPathResource(dbLocation);
        File database = res.getFile();
        this.dbReader = new DatabaseReader.Builder(database)
                .build();

        Log.getLogger().info("Location Database has been built");
    }

    public Location getLocationById(String ip){
        Location location = new Location();
        try{
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = this.dbReader.city(ipAddress);
//            System.out.println(response);
            if(response != null)
            {
                if(!response.getCountry().getName().isEmpty()) location.setCountryName(response.getCountry().getName());
                if(!response.getSubdivisions().isEmpty()) location.setProvinceName(response.getSubdivisions().get(0).getName());
                if(!response.getCity().getName().isEmpty()) location.setCityName(response.getCity().getName());
                if(!response.getPostal().getCode().isEmpty()) location.setPostal(response.getPostal().getCode());
                if(!response.getLeastSpecificSubdivision().getName().isEmpty()) location.setState(response.getLeastSpecificSubdivision().getName());
            }

        }
        catch (NullPointerException|IOException|GeoIp2Exception e)
        {
            Log.getLogger().error("Location by ip is not found, "+e.toString());
        }


        return location;
    }


}
