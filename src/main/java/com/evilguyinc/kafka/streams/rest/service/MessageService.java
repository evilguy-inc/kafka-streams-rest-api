package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private TopicCache topicCache;

    public void putMessage(String topic, String key, ObjectNode value) {
        topicCache.putMessage(topic, key, value);
    }


    public List<ObjectNode> getAllMessages(String topic) {
        return topicCache.getAllMessages(topic);
    }

    public List<ObjectNode> getMessages(String topic, Integer start, Long lenght) {
        List<ObjectNode> allMessages = topicCache.getAllMessages(topic);

        List<ObjectNode> result = new ArrayList<>();

        for (int i = start;
             i < allMessages.size() && i < (start + lenght); i++) {

            result.add(allMessages.get(i));
        }

        return result;
    }


    public List<ObjectNode> getMessage(String topic, String key) {
        return topicCache.getMessage(topic, key);
    }


    public List<ObjectNode> findMessages(String topic, String searchTag, Long limit) {
        return topicCache.getAllMessages(topic)
                .parallelStream()
                .limit(limit)
                .filter(jsonObject -> isFoundElement(jsonObject.elements(), searchTag))
                .collect(Collectors.toList());
    }


    private boolean isFoundElement(Iterator<JsonNode> elements, String searchTag) {

        while (elements.hasNext()) {
            JsonNode element = elements.next();

            if (element instanceof ObjectNode)
                return isFoundElement(element.elements(), searchTag);

            if (searchTag.equals(element.asText()))
                return true;
        }

        return false;
    }
}
