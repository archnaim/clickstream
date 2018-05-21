package acn.clickstream.dsc.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "acn.clickstream.dsc" })
@EntityScan("acn.clickstream.dsc")
@EnableJpaRepositories("acn.clickstream.dsc")
@EnableDiscoveryClient
public class ClickstreamHubAplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickstreamHubAplication.class, args);
    }
}
