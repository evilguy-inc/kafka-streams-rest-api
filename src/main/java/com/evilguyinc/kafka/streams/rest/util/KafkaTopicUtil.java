package com.evilguyinc.kafka.streams.rest.util;

import static com.evilguyinc.kafka.streams.rest.constants.DefaultKafkaConstants.DEFAULT_KAFKA_TOPIC_PATTERN;

public class KafkaTopicUtil {

    public static boolean isNotDefaultKafkaTopic(String topic){
        return !topic.startsWith(DEFAULT_KAFKA_TOPIC_PATTERN);
    }
}
