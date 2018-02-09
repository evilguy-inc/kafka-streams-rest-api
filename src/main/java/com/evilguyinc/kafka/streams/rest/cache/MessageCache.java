package com.evilguyinc.kafka.streams.rest.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MessageCache {

    private String topic;
    private Map<String, List<Object>> messageCache;
    private List<Object> messageListCache;

    private MessageCache() {
    }

    private void setTopic(String topic) {
        this.topic = topic;
    }

    private void setMessageCache(Map<String, List<Object>> messageCache, List<Object> messageListCache) {
        this.messageCache = messageCache;
        this.messageListCache = messageListCache;
    }

    public void putMessage(String key, Object value) {
        if (!messageCache.containsKey(key)) {
            messageCache.put(key, new ArrayList<>());
        }

        messageCache.get(key).add(value);
        messageListCache.add(value);
    }

    public List<Object> getMessageCache(){
        return messageListCache;
    }


    public static MessageCache createMessageCache(String topic) {

        MessageCache messageCache = new MessageCache();
        messageCache.setTopic(topic);
        messageCache.setMessageCache(new HashMap<>(), new ArrayList<>());

        return messageCache;
    }
}
