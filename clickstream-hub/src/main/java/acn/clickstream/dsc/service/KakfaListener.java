package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.FaqSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class KakfaListener {

    @Autowired
    private DbOperation dbOperation;

    @Autowired
    private RedisOperation redisOperation;

    @KafkaListener(topics = "${kafka.topic.clickstream}",groupId = "${kafka.group.consumerClickstream}", containerFactory = "kafkaListenerContainerFactory")
    public void listenClickstreamPayload(String payload)
    {
        dbOperation.saveOrUpdateClickStream(payload);
    }

    @KafkaListener(topics = "${kafka.topic.faqcount}",groupId = "${kafka.group.consumerFaqCount}", containerFactory = "kafkaListenerContainerFactory")
    public void listenFaqCount(@Payload String payload, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key)
    {
        String[] keys = key.split("\\|\\|");
//        dbOperation.saveOrUpdateFaqSummary(new FaqSummary(keys[0],keys[1], Long.valueOf(payload), LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        redisOperation.saveOrUpdateFaqSummary(new FaqSummary(keys[0],keys[1], Long.valueOf(payload), LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }

}
