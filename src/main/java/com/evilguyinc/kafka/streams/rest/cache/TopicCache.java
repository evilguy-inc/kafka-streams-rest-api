package com.evilguyinc.kafka.streams.rest.cache;

import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.exception.ResourceNotFoundException;

import java.util.*;

public class TopicCache {

    private Map<String, MessageCache> topicCache;

    public TopicCache() {
        topicCache = new HashMap<>();
    }


    public void addTopic(String topicName) {
        // TODO add topic serde, it might be different
        Topic topic = new Topic(topicName, "Serde???");
        topicCache.put(topicName, MessageCache.createMessageCache(topic));
    }

    public void putMessage(String topic, String key, Object value) {

        topicCache.get(topic).putMessage(key, value);
    }


    public Set<String> getAllTopics() {
        return topicCache.keySet();
    }


    public List<Object> getAllMessages(String topic) {
        return getMessageCache(topic)
                .getMessageCache();
    }


    public List<Object> getMessage(String topic, String key) {
        return getMessageCache(topic)
                .getMessage(key);
    }


    private MessageCache getMessageCache(String topic) {
        return Optional.ofNullable(topicCache.get(topic))
                .orElseThrow(() -> new ResourceNotFoundException("Topic is not being read."));
    }
}
