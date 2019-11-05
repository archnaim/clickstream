package acn.clickstream.dsc.serde;

import acn.clickstream.dsc.model.ClickstreamPayload;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ClickStreamSerde implements Serde<ClickstreamPayload> {

    private ClickStreamSerializer serializer = new ClickStreamSerializer();
    private ClickStreamDeserializer deserializer = new ClickStreamDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<ClickstreamPayload> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<ClickstreamPayload> deserializer() {
        return deserializer;
    }
}
