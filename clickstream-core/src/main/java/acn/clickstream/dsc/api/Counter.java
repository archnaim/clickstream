package acn.clickstream.dsc.api;

import acn.clickstream.dsc.model.ClickstreamPayload;
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
        logger.debug("Received request to user_count_per_hour");
        Set<TimeUserCount> timeUserCountSet = queryUserCount.getTimeUserCounts();
        logger.info(String.format("Completed query for user_count_per_hour, result: %s", timeUserCountSet.toString()));
        return timeUserCountSet;
    }

    @PostMapping("/coba")
    public String coba(@RequestBody ClickstreamPayload clickstreamPayload) {
        return "coba";
    }
}
