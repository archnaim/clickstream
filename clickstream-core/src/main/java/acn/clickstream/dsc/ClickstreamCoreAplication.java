package acn.clickstream.dsc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ClickstreamCoreAplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickstreamCoreAplication.class, args);
    }
}

