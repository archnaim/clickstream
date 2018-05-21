package acn.clickstream.dsc.config;

import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.stream.StreamProc;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;

import java.util.Properties;

@Configuration
public class KafkaStreamConfiguration {
    @Value("${kafka.application.id}")
    private String APPLICATION_ID_CONFIG;

    @Value("${acn.kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Value("${kafka.auto_offset_reset_config}")
    private String AUTO_OFFSET_RESET_CONFIG;

    @Autowired
    private StreamProc streamProc;

    @Bean
    public StreamsConfig streamsConfigs() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
//        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);


        return new StreamsConfig(props);
    }

    @Bean
    public StreamsBuilderFactoryBean streamsBuilder(StreamsConfig streamsConfig)
    {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }

    @Bean
    public KStream<String, ClickstreamPayload> streamGetLocationBuild(StreamsBuilder streamsBuilder) {
        return streamProc.streamingCountAndGetLocation(streamsBuilder);
    }
}
