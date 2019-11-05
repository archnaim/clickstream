package acn.clickstream.dsc.service;

import acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;
import acn.clickstream.dsc.model.TimeUserCount;
import acn.clickstream.dsc.model.UserCount;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static acn.clickstream.dsc.util.UtilityService.waitUntilStoreIsQueryable;

@Component
public class QueryStateStoreImpl implements QueryStateStore {

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Override
    public Set<TimeUserCount> getTimeUserCounts() {
        KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();

        ReadOnlyWindowStore<String, Long> userCountTable = null;
        try {
            userCountTable = waitUntilStoreIsQueryable(
                    KafkaStreamingSinkTopic.USER_COUNT_PER_HOUR, QueryableStoreTypes.windowStore(), kafkaStreams);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long timeFrom = 0; // beginning of time = oldest available
        long timeTo = System.currentTimeMillis(); // now (in processing-time)

        HashMap<Long, List<UserCount>> hashMap = new HashMap<>();


        KeyValueIterator<Windowed<String>, Long> fetched = userCountTable.fetchAll(timeFrom, timeTo);

        while (fetched != null && fetched.hasNext()) {
            KeyValue<Windowed<String>, Long> next = fetched.next();
            Long timestamp = next.key.window().start();
            String user = next.key.key();
            Long counter = next.value;

            UserCount userCount = new UserCount(user, counter);

            if (!hashMap.containsKey(timestamp)) {
                List<UserCount> userCounts = new ArrayList<>();
                userCounts.add(userCount);
                hashMap.put(timestamp, userCounts);
            } else {
                List<UserCount> userCounts = hashMap.get(timestamp);
                if (userCounts == null) {
                    userCounts = new ArrayList<>();
                    userCounts.add(userCount);
                } else {
                    userCounts.add(userCount);
                }
            }
        }
        if (fetched != null) fetched.close();

        Set<TimeUserCount> timeUserCountSet = hashMap
                .entrySet()
                .parallelStream()
                .map(TimeUserCount::new)
                .collect(Collectors.toSet());

        return timeUserCountSet;
    }
}