package acn.clickstream.dsc.service;

import acn.clickstream.dsc.constant.KafkaStreamingSinkTopic;
import acn.clickstream.dsc.model.TimeUniqueCount;
import acn.clickstream.dsc.model.TimeUserCount;
import acn.clickstream.dsc.model.UserCount;
import acn.clickstream.dsc.singleton.Singleton;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static acn.clickstream.dsc.util.UtilityService.waitUntilStoreIsQueryable;

@Component
public class QueryStateStoreImpl implements QueryStateStore {

    @Resource(name = "&userCountBuilder")
    private StreamsBuilderFactoryBean userCountBuilder;

    @Resource(name = "&visitCountBuilder")
    private StreamsBuilderFactoryBean visitCountBuilder;

    @Override
    public Set<TimeUserCount> getTimeUserCounts() {
        KafkaStreams kafkaStreams = userCountBuilder.getKafkaStreams();

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

    @Override
    public Set<TimeUniqueCount> getVisitCount() {
        KafkaStreams kafkaStreams = visitCountBuilder.getKafkaStreams();

        ReadOnlyWindowStore<String, Long> visitCountTable = null;
        try {
            visitCountTable = waitUntilStoreIsQueryable(
                    KafkaStreamingSinkTopic.SESSION_VISIT_PER_HOUR, QueryableStoreTypes.windowStore(), kafkaStreams);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long timeFrom = 0; // beginning of time = oldest available
        long timeTo = System.currentTimeMillis(); // now (in processing-time)

        HashMap<Long, Long> hashMap = new HashMap<>();

        KeyValueIterator<Windowed<String>, Long> fetched = visitCountTable.fetchAll(timeFrom, timeTo);

        while (fetched != null && fetched.hasNext()) {
            KeyValue<Windowed<String>, Long> next = fetched.next();
            long timestamp = next.key.window().start();
            Long count = next.value;

            if (hashMap.containsKey(timestamp)) {
                Long newValue = hashMap.get(timestamp) + count;
                hashMap.replace(timestamp, newValue);
            } else hashMap.put(timestamp, count);
        }

        Set<TimeUniqueCount> timeUniqueCount = hashMap
                .entrySet()
                .parallelStream()
                .map(TimeUniqueCount::new)
                .collect(Collectors.toSet());

        return timeUniqueCount;
    }

    @Override
    @DependsOn("bounceTopology")
    public Set<TimeUniqueCount> getBounceCount() {
        KafkaStreams kafkaStreams = visitCountBuilder.getKafkaStreams();

        ReadOnlyWindowStore<String, Long> bounceCountTable = null;
        try {
            bounceCountTable = waitUntilStoreIsQueryable(
                    Singleton.getInstance().BOUNCE_VISIT_PER_HOUR, QueryableStoreTypes.windowStore(), kafkaStreams);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long timeFrom = 0; // beginning of time = oldest available
        long timeTo = System.currentTimeMillis(); // now (in processing-time)

        HashMap<Long, Long> hashMap = new HashMap<>();

        KeyValueIterator<Windowed<String>, Long> fetched = bounceCountTable.fetchAll(timeFrom, timeTo);

        while (fetched != null && fetched.hasNext()) {
            KeyValue<Windowed<String>, Long> next = fetched.next();
            long timestamp = next.key.window().start();
            Long count = next.value;

            if (hashMap.containsKey(timestamp)) {
                Long newValue = hashMap.get(timestamp) + count;
                hashMap.replace(timestamp, newValue);
            } else hashMap.put(timestamp, count);

        }

        Set<TimeUniqueCount> timeUniqueCount = hashMap
                .entrySet()
                .parallelStream()
                .map(TimeUniqueCount::new)
                .collect(Collectors.toSet());

        return timeUniqueCount;
    }

    @Override
    public void getFirstLastPage() {

    }
}