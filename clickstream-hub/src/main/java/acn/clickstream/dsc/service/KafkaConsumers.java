package acn.clickstream.dsc.service;

import acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumers {

    @Autowired
    private BounceVisitDao bounceVisitDao;

    @Autowired
    private UniqueVisitDao uniqueVisitDao;

    @KafkaListener(topics = KafkaStreamingSinkTopic.SESSION_VISIT_PER_HOUR, groupId = "${kafka.consumer.group_id}")
    public void listenUniqueVisit(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String value) {
        uniqueVisitDao.insert(Long.valueOf(key), Long.valueOf(value));
    }


    @KafkaListener(topics = KafkaStreamingSinkTopic.BOUNCE_VISIT_PER_HOUR, groupId = "${kafka.consumer.group_id}")
    public void listenBounceVisit(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String value) {
        bounceVisitDao.insert(Long.valueOf(key), Long.valueOf(value));
    }

}
