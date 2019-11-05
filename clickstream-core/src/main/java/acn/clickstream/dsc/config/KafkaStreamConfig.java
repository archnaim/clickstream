package acn.clickstream.dsc.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaStreamConfig {
    @Value("${kafka.application.id}")
    private String APPLICATION_ID_CONFIG;

    @Value("${acn.kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Bean
    public KafkaStreamsConfiguration streamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
//        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        return new KafkaStreamsConfiguration(props);
    }

    @Bean("userCountBuilder")
    public StreamsBuilderFactoryBean userCountBuilder(KafkaStreamsConfiguration streamsConfig)
    {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }
}
