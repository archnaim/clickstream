package acn.clickstream.dsc.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@Configuration
public class KafkaStreamConfig {
    @Value("${kafka.application.id}")
    private String APPLICATION_ID_CONFIG;

    @Value("${acn.kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Value("${kafka.auto_offset_reset_config}")
    private String AUTO_OFFSET_RESET_CONFIG;

    @Bean
    public KafkaStreamsConfiguration streamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
//        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);


        return new KafkaStreamsConfiguration(props);
        // return new StreamsConfig(props);
    }

    @Bean("userCountBuilder")
    public StreamsBuilderFactoryBean userCountBuilder(KafkaStreamsConfiguration streamsConfig)
    {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }
}
