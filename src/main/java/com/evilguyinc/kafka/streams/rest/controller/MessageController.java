package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.deserializer.AvroDeserializer;
import com.evilguyinc.kafka.streams.rest.exception.KafkaStreamsRestException;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.evilguyinc.kafka.streams.rest.constants.StreamConfigurations.DEFAULT_STREAM_SEARCH_RESULT_LIMIT;
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
    public List<ObjectNode> getAllMessages(
            @RequestParam(value = "topic") String topic
    ) {

        List<ObjectNode> messages = messageService.getAllMessages(topic);

        // TODO check if topic is avro serde

        return messages;
    }


    @RequestMapping(value = "/period", method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public List<ObjectNode> getMessages(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "start") Integer start,
            @RequestParam(value = "lenght") Long lenght
    ) {

        if (start < 0 | lenght <= 0)
            throw new KafkaStreamsRestException("Start index and Lenght cannot be negative values.");

        List<ObjectNode> messages = messageService.getMessages(topic, start, lenght);

        // TODO check if topic is avro serde

        return messages;
    }


    @RequestMapping(method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public List<ObjectNode> getMessage(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "key") String key
    ) {

        List<ObjectNode> messages = Optional.ofNullable(messageService.getMessage(topic, key))
                .orElse(Arrays.asList());

        return messages;
    }


    @RequestMapping(value = "/find", method = GET, produces = "application/json")
    @ResponseStatus(OK)
    public List<ObjectNode> findMessages(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "searchTag") String searchTag,
            @RequestParam(value = "limit", required = false,
                    defaultValue = DEFAULT_STREAM_SEARCH_RESULT_LIMIT) Long limit
    ) {

        if (StringUtils.isEmpty(searchTag))
            return messageService.getMessages(topic, 0, limit);

        List<ObjectNode> messages = Optional.ofNullable(messageService.findMessages(topic, searchTag, limit))
                .orElse(Arrays.asList());

        return messages;
    }


    // TODO last state of elements streams into table format by key

}
