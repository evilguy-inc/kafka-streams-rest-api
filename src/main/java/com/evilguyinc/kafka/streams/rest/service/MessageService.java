package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private TopicCache topicCache;

    public void putMessage(String topic, String key, Object value){
        topicCache.putMessage(topic,key,value);
    }


    public List<Object> getAllMessages(String topic){
        return topicCache.getAllMessages(topic);
    }

    public List<Object> getMessages(String topic, Integer start, Integer lenght){
        List<Object> allMessages = topicCache.getAllMessages(topic);

        List<Object> result = new ArrayList<>();

        for (int i = start;
             i < allMessages.size() && i < (start+lenght); i++){

            result.add(allMessages.get(i));
        }

        return result;
    }

}
