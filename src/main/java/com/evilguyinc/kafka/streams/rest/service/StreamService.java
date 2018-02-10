package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.properties.StreamProperties;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Service
public class StreamService {

    private final Logger logger = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private StreamProperties streamProperties;
    @Autowired
    private MessageService messageService;

    private KafkaStreams streams;


    @PreDestroy
    public void closeStream() {
        logger.info("Terminating event stream.");
        streams.close();
    }


    public void readTopic(Topic topic) {

        Properties streamProperties = this.streamProperties.getStreamProperties();

        // TODO: change serde to a different if not avro

        StreamsBuilder streamBuilder = new StreamsBuilder();

        streamBuilder.stream(topic.getTopic())
                .map((key, value) -> {
                    if (key instanceof GenericData.Record) {

                        messageService.putMessage(topic.getTopic(), key.toString(), value);

                    }

                    return new KeyValue<>(key, value);
                });


        KafkaStreams streams = new KafkaStreams(streamBuilder.build(), streamProperties);

        logger.info("Starting event streaming.");
        streams.start();
    }


}
