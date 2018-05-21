package acn.clickstream.dsc.stream;

import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.model.FaqSummary;
import acn.clickstream.dsc.util.Log;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class StreamProc {

    @Value("${kafka.topic.faqcount}")
    private String FAQ_COUNT;

    @Value("${kafka.topic.clickstream}")
    private String CLICKSTREAM_TOPIC;

    private Serde<String> stringSerde = Serdes.String();

    private JsonSerde<FaqSummary> faqSummaryJsonSerde = new JsonSerde<>(FaqSummary.class);


    private JsonSerde<ClickstreamPayload> clickStreamSerde = new JsonSerde<>(ClickstreamPayload.class);

    public KStream<String, ClickstreamPayload> streamingCountAndGetLocation(StreamsBuilder kStreamBuilder) {
        Log.getLogger().info("Starting Streaming Process");
        KStream<String,ClickstreamPayload> kStream =
                kStreamBuilder.stream(CLICKSTREAM_TOPIC, Consumed.with(stringSerde,clickStreamSerde));


        //Processing most clicked FAQ
        KTable<String,Long> faqCount= kStream
                .map((k,v)-> new KeyValue<>(v.getFaqId()+"||"+v.getUserType(),v))
                .groupByKey(Serialized.with(stringSerde,clickStreamSerde))
                .count();

        faqCount.toStream()
                .mapValues(String::valueOf)
                .to(FAQ_COUNT,Produced.with(stringSerde, stringSerde));

//        //Processing
//        kStream.to("topic2", Produced.with(stringSerde,clickStreamSerde));

        return kStream;
    }



}
