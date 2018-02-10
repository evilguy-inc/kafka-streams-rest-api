package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.deserializer.AvroDeserializer;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AvroDeserializer avroDeserializer;

    @RequestMapping(method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public String getAllMessages(
            @RequestParam(value = "topic") String topic
    ) {

        List<Object> messages = messageService.getAllMessages(topic);

        // TODO check if topic is avro serde

        // Avro serde deserialiser into JSON
        return avroDeserializer.deserilze(messages);
    }


}
