package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.util.Log;
import acn.clickstream.dsc.util.UtilityService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.topic.clickstream}")
    private String CLICKSTREAM_TOPIC;

    public Object send(String message) {
        Log.getLogger().info("Send message to topic, "+message);
        try {
            objectMapper.readValue(message,ClickstreamPayload.class);
            kafkaTemplate.send(CLICKSTREAM_TOPIC,message);
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in sending click stream payload to kafka topic, "+e.toString());
            return UtilityService.buildHttpErrorMessage(e);
        }
        return HttpStatus.ACCEPTED;
    }
}
