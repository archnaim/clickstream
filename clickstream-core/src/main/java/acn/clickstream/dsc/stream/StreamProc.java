package acn.clickstream.dsc.stream;

import acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;
import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.singleton.Singleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StreamProc {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.topic.clickstream}")
    private String CLICKSTREAM_TOPIC;

    private Serde<String> stringSerde = Serdes.String();

    private Logger logger = LoggerFactory.getLogger(StreamProc.class);

//    private JsonSerde<ClickstreamPayload> clickStreamSerde = new JsonSerde<>(ClickstreamPayload.class);

    @Bean("userCountTopology")
    public KStream<String, String> streamingUserCountPerHour(
            @Qualifier("userCountBuilder") StreamsBuilder kStreamBuilder) {
        logger.info("Starting userCount Streaming Process");
        final KStream<String, String> kStream =
                kStreamBuilder.stream(CLICKSTREAM_TOPIC, Consumed
                        .with(stringSerde, stringSerde));

        KTable<Windowed<String>,Long> userCountTable = kStream
                .peek((k, v) -> System.out.println(v))
                .map((key, value) -> {
                    ClickstreamPayload clickstreamPayload = null;
                    try {
                        clickstreamPayload = objectMapper.readValue(value, ClickstreamPayload.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return KeyValue.pair(clickstreamPayload != null ? clickstreamPayload.getUser_id() : "null", value);
                })
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(60)))
                .count(Materialized
                        .as(KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR));

        userCountTable
                .toStream()
                .map((key, value) -> KeyValue.pair(key.toString(), value.toString()))
                .to(KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR);

        return kStream;
    }


    @Bean("bounceTopology")
    public KStream<String, String> streamingBouncePerHour(
            @Qualifier("visitCountBuilder") StreamsBuilder kStreamBuilder) {
        logger.info("Starting Bounce Streaming Process");

        final KStream<String, String> kStream =
                kStreamBuilder.stream(CLICKSTREAM_TOPIC, Consumed
                        .with(stringSerde, stringSerde));

        KGroupedStream<String, String> sessionIpGroupedStream = kStream.map((key, value) -> {
            ClickstreamPayload clickstreamPayload = null;
            try {
                clickstreamPayload = objectMapper.readValue(value, ClickstreamPayload.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return KeyValue.pair(
                    String.format("%s_%s", clickstreamPayload != null ? clickstreamPayload.getSession_id() : "null"
                            , clickstreamPayload != null ? clickstreamPayload.getIp_address() : "null")
                    , value);
        }).groupByKey();

        KTable<Windowed<String>, Long> visitPerHourTable = sessionIpGroupedStream
                .windowedBy(TimeWindows.of(Duration.ofMinutes(60)))
                .count(Materialized.as(KafkaStreamingSinkTopic.SESSION_VISIT_PER_HOUR));


        Singleton.getInstance().BOUNCE_VISIT_PER_HOUR = visitPerHourTable
                .filter((k, v) -> v == 1)
                .queryableStoreName();

        return kStream;
    }
}
