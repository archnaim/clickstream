package acn.clickstream.dsc.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "acn.clickstream.dsc" })
@EntityScan("acn.clickstream.dsc")
public class ClickstreamHubAplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickstreamHubAplication.class, args);
    }
}
