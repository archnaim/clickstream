package acn.clickstream.dsc.api;

import acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;
import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.model.TimeUniqueCount;
import acn.clickstream.dsc.model.TimeUserCount;
import acn.clickstream.dsc.service.QueryStateStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class Counter {

    @Autowired
    private QueryStateStore queryUserCount;

    private Logger logger = LoggerFactory.getLogger(Counter.class);

    @PostMapping("/user_per_window_time")
    public Set<TimeUserCount> getTimeUserCounts() {
        logger.debug(String.format("Received request to %s", KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR));
        Set<TimeUserCount> timeUserCountSet = queryUserCount.getTimeUserCounts();
        logger.info(String.format("Completed query for %s, result: %s",
                KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR, timeUserCountSet.toString()));
        return timeUserCountSet;
    }

    @PostMapping("/visit_per_window_time")
    public Set<TimeUniqueCount> getTimeVisitCounts() {
        logger.debug(String.format("Received request to %s", KafkaStreamingSinkTopic.SESSION_VISIT_PER_HOUR));
        Set<TimeUniqueCount> timeUniqueCount = queryUserCount.getVisitCount();
        logger.info(String.format("Completed query for %s, result: %s",
                KafkaStreamingSinkTopic.SESSION_VISIT_PER_HOUR, timeUniqueCount.toString()));
        return timeUniqueCount;
    }

    @PostMapping("/bounce_per_window_time")
    public Set<TimeUniqueCount> getTimeBounceCounts() {
        logger.debug(String.format("Received request to %s", KafkaStreamingSinkTopic.BOUNCE_VISIT_PER_HOUR));
        Set<TimeUniqueCount> timeUniqueCount = queryUserCount.getBounceCount();
        logger.info(String.format("Completed query for %s, result: %s",
                KafkaStreamingSinkTopic.BOUNCE_VISIT_PER_HOUR, timeUniqueCount.toString()));
        return timeUniqueCount;
    }

    @PostMapping("/coba")
    public String coba(@RequestBody ClickstreamPayload clickstreamPayload) {
        return "coba";
    }
}
