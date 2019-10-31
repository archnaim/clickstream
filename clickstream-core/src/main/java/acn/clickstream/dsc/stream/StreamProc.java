package acn.clickstream.dsc.stream;

import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.util.Log;
import main.java.acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class StreamProc {

    @Value("${kafka.topic.clickstream}")
    private String CLICKSTREAM_TOPIC;

    private Serde<String> stringSerde = Serdes.String();

    private JsonSerde<ClickstreamPayload> clickStreamSerde = new JsonSerde<>(ClickstreamPayload.class);

    @Bean("userCountTopology")
    public KStream<String, ClickstreamPayload> streamingUserCountPerHour(
            @Qualifier("userCountBuilder") StreamsBuilder kStreamBuilder) {
        Log.getLogger().info("Starting Streaming Process");
        final KStream<String,ClickstreamPayload> kStream =
                kStreamBuilder.stream(CLICKSTREAM_TOPIC, Consumed
                                                .with(stringSerde, clickStreamSerde));

        KTable<Windowed<String>,Long> userCountTable = kStream
            .map((key,value)->KeyValue.pair(value.getUser_id(),value))
            .groupByKey()
            .windowedBy(TimeWindows.of(Duration.ofMinutes(60)))
            .count(Materialized
                .<String, Long, WindowStore<Bytes, byte[]>>as(KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR));

        userCountTable
            .toStream()
            .to(KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR);


        return kStream;
    }





}
