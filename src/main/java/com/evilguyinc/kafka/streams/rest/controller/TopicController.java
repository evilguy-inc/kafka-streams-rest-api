package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import com.evilguyinc.kafka.streams.rest.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public void readTopic(@RequestBody Topic topic){

        streamService.readTopic(topic);
    }


    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    public Set<Topic> getAllTopics(){
        return messageService.getAllTopics();
    }


}
