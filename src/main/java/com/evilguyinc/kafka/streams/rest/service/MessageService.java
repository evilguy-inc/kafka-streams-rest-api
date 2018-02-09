package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import com.evilguyinc.kafka.streams.rest.domain.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private TopicCache topicCache;

    @PostConstruct
    public void init(){
        topicCache = new TopicCache();
    }

    public void putMessage(String topic, String key, Object value){
        topicCache.putMessage(topic,key,value);
    }

    public Set<Topic> getAllTopics(){
       return topicCache.getAllTopics().stream()
               .map(Topic::new)
               .collect(Collectors.toSet());
    }

    public List<Object> getAllMessages(String topic){
        return topicCache.getAllMessages(topic);
    }

}
