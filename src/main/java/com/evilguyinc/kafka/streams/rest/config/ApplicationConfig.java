package com.evilguyinc.kafka.streams.rest.config;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import com.evilguyinc.kafka.streams.rest.deserializer.AvroDeserializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootConfiguration
@ComponentScan(basePackages = {
        "com.evilguyinc.kafka.streams.rest.properties",
        "com.evilguyinc.kafka.streams.rest.controller",
        "com.evilguyinc.kafka.streams.rest.service"
})
public class ApplicationConfig {

    private TopicCache topicCache;

    @PostConstruct
    public void init(){
        topicCache = new TopicCache();
    }

    @Bean
    public TopicCache getTopicCache(){
        return this.topicCache;
    }

    @Bean
    public AvroDeserializer getAvroDeserializer(){
        return new AvroDeserializer();
    }
}
