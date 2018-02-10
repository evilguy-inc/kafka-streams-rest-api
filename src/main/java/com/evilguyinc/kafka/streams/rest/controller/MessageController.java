package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.deserializer.AvroDeserializer;
import com.evilguyinc.kafka.streams.rest.exception.KafkaStreamsRestException;
import com.evilguyinc.kafka.streams.rest.exception.ResourceNotFoundException;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AvroDeserializer avroDeserializer;

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public String getAllMessages(
            @RequestParam(value = "topic") String topic
    ) {

        List<Object> messages = messageService.getAllMessages(topic);

        // TODO check if topic is avro serde

        // Avro serde deserialiser into JSON
        return avroDeserializer.deserilze(messages);
    }


    @RequestMapping(value = "/period", method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public String getMessages(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "start") Integer start,
            @RequestParam(value = "lenght") Integer lenght
    ) {

        if ( start < 0 | lenght <= 0)
            throw new KafkaStreamsRestException("Start index and Lenght cannot be negative values.");

        List<Object> messages = messageService.getMessages(topic, start, lenght);

        // TODO check if topic is avro serde

        // Avro serde deserialiser into JSON
        return avroDeserializer.deserilze(messages);
    }


    // TODO find all messages by key
    @RequestMapping(method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public String getMessage(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "key") String key
    ) {


        List<Object> messages = Optional.ofNullable(messageService.getMessage(topic, key))
                .orElseThrow(() -> new ResourceNotFoundException("Message with required key " + key + " doesn't exist."));

        // TODO check if topic is avro serde

        // Avro serde deserialiser into JSON
        return avroDeserializer.deserilze(messages);
    }


    // TODO last state of elements streams into table format by key

    // TODO find element not by key
}
