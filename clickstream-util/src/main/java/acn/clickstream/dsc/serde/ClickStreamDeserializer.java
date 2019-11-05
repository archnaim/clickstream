package acn.clickstream.dsc.serde;

import acn.clickstream.dsc.model.ClickstreamPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class ClickStreamDeserializer implements Deserializer<ClickstreamPayload> {
    private boolean isKey;

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.isKey = isKey;
    }

    @Override
    public ClickstreamPayload deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.readValue(data, ClickstreamPayload.class);

        } catch (IOException | RuntimeException e) {
            throw new SerializationException("Error deserializing value", e);
        }
    }
}
