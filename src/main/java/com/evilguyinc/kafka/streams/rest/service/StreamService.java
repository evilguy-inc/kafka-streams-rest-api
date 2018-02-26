package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import com.evilguyinc.kafka.streams.rest.deserializer.AvroJsonConverter;
import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.properties.StreamProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.evilguyinc.kafka.streams.rest.util.KafkaTopicUtil.isNotDefaultKafkaTopic;

@Service
public class StreamService {

    private final Logger logger = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private StreamProperties streamProperties;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TopicCache topicCache;
    @Autowired
    private AvroJsonConverter avroJsonConverter;

    private List<KafkaStreams> streams;

    @PostConstruct
    public void init() {
        streams = new ArrayList<>();
        shutdownHook();
    }


    @PreDestroy
    public void closeStream() {
        logger.info("Terminating event stream.");
        streams.forEach(KafkaStreams::close);
    }

    private void shutdownHook() {
        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(() -> streams.forEach(KafkaStreams::close)));
    }


    public void readTopic(Topic topic) {

        Properties streamProperties = this.streamProperties.getStreamProperties();

        // TODO: change serde to a different if not avro

        StreamsBuilder streamBuilder = new StreamsBuilder();

        streamBuilder.stream(topic.getTopic())
                .map((key, value) -> {
                    if (key instanceof GenericData.Record) {

                        ObjectNode jsonKey = avroJsonConverter.getJsonFrom((GenericData.Record) key);
                        ObjectNode jsonValue = avroJsonConverter.getJsonFrom((GenericData.Record) value);

                        messageService.putMessage(topic.getTopic(),
                                jsonKey.asText(), jsonValue);

                    }

                    return new KeyValue<>(key, value);
                });


        KafkaStreams kafkaStreams = new KafkaStreams(streamBuilder.build(), streamProperties);
        streams.add(kafkaStreams);

        logger.info("Starting event streaming.");
        topicCache.addTopic(topic.getTopic());
        kafkaStreams.start();

    }


    public Map<String, List<PartitionInfo>> getUserKafkaTopics() {


        return getAllKafkaTopics()
                .entrySet().stream()
                .filter(topic -> isNotDefaultKafkaTopic(topic.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public Map<String, List<PartitionInfo>> getAllKafkaTopics() {

        Properties streamProperties = this.streamProperties.getKafkaConsumerProperties();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(streamProperties);
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();
        consumer.close();

        return topics;
    }

}
