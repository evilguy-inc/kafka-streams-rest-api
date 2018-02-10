package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import com.evilguyinc.kafka.streams.rest.service.StreamService;
import com.evilguyinc.kafka.streams.rest.service.TopicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private StreamService streamService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ObjectWriter objectWriter;


    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public void readTopic(@RequestBody Topic topic) {

        // TODO: check if topic exists before start reading it
        streamService.readTopic(topic);
    }


    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    public Set<String> getAllTopics() {
        return topicService.getAllTopics();
    }

    @RequestMapping(value = "/kafka", method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public String getAllKafkaTopics(@RequestParam(value = "all", required = false) Boolean all) throws JsonProcessingException {

        Map<String, List<PartitionInfo>> topics = (allNotNull(all) && all)
                ? streamService.getAllKafkaTopics()
                : streamService.getUserKafkaTopics();

        return objectWriter.writeValueAsString(topics);
    }

    // TODO: delete topic


}
