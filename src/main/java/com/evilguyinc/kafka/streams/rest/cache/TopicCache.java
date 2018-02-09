package com.evilguyinc.kafka.streams.rest.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TopicCache {

    private Map<String, MessageCache> topicCache;

    public TopicCache() {
        topicCache = new HashMap<>();
    }


    private void addTopic(String topic) {
        topicCache.put(topic, MessageCache.createMessageCache(topic));
    }

    public void putMessage(String topic, String key, Object value) {
        if (!topicCache.containsKey(topic))
            addTopic(topic);

        topicCache.get(topic).putMessage(key, value);
    }


    public Set<String> getAllTopics(){
        return topicCache.keySet();
    }


    public List<Object> getAllMessages(String topic){
        return topicCache.get(topic).getMessageCache();
    }

}
