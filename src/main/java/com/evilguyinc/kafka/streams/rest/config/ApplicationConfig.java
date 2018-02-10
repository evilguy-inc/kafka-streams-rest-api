package com.evilguyinc.kafka.streams.rest.config;

import com.evilguyinc.kafka.streams.rest.cache.TopicCache;
import com.evilguyinc.kafka.streams.rest.deserializer.AvroDeserializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        topicCache = new TopicCache();

        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Bean
    public TopicCache getTopicCache(){
        return this.topicCache;
    }

    @Bean
    public AvroDeserializer getAvroDeserializer(){
        return new AvroDeserializer();
    }


    @Bean
    private ObjectMapper getObjectMapper(){
        return objectMapper;
    }

    @Bean
    public ObjectWriter getObjectWriter(){
        return getObjectMapper().writer();
    }
}
