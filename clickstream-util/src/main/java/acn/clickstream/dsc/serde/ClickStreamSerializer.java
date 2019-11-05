package acn.clickstream.dsc.serde;

import acn.clickstream.dsc.model.ClickstreamPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ClickStreamSerializer implements Serializer<ClickstreamPayload> {
    private boolean isKey;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.isKey = isKey;
    }

    @Override
    public byte[] serialize(String topic, ClickstreamPayload data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing value", e);
        }

    }

    @Override
    public void close() {

    }
}
