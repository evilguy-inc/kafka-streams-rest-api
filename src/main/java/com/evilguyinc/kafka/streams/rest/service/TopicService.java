package com.evilguyinc.kafka.streams.rest.service;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TopicService {

    @Autowired
    private TopicCache topicCache;

    public Set<String> getAllTopics(){
        return topicCache.getAllTopics();
    }
}
