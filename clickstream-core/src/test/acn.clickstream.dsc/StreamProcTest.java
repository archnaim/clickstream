//package acn.clickstream.dsc;
//
//import org.apache.kafka.common.serialization.*;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.Topology;
//import org.apache.kafka.streams.TopologyTestDriver;
//import org.apache.kafka.streams.state.KeyValueStore;
//import org.apache.kafka.streams.state.Stores;
//import org.apache.kafka.streams.test.ConsumerRecordFactory;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//
//import java.util.Properties;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(JUnit4.class)
//class StreamProcTest {
//
//    private TopologyTestDriver testDriver;
//    private KeyValueStore<String, Long> store;
//
//    private StringDeserializer stringDeserializer = new StringDeserializer();
//    private LongDeserializer longDeserializer = new LongDeserializer();
//    private ConsumerRecordFactory<String, Long> recordFactory = new ConsumerRecordFactory<>(new StringSerializer(), new LongSerializer());
//
//    @Before
//    public void setup() {
//        Topology topology = new Topology();
//        topology.addSource("sourceProcessor", "input-topic");
//        topology.addProcessor("aggregator", new CustomMaxAggregatorSupplier(), "sourceProcessor");
//        topology.addStateStore(
//                Stores.keyValueStoreBuilder(
//                        Stores.inMemoryKeyValueStore("aggStore"),
//                        Serdes.String(),
//                        Serdes.Long()).withLoggingDisabled(), // need to disable logging to allow store pre-populating
//                "aggregator");
//        topology.addSink("sinkProcessor", "result-topic", "aggregator");
//
//        // setup test driver
//        Properties props = new Properties();
//        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "maxAggregation");
//        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
//        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
//        testDriver = new TopologyTestDriver(topology, props);
//
//        // pre-populate store
//        store = testDriver.getKeyValueStore("aggStore");
//        store.put("a", 21L);
//    }
//
//    @After
//    public void tearDown() {
//        testDriver.close();
//    }
//
//}