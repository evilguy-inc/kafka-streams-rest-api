package com.evilguyinc.kafka.streams.rest.controller;

import com.evilguyinc.kafka.streams.rest.domain.Topic;
import com.evilguyinc.kafka.streams.rest.service.MessageService;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ObjectWriter objectWriter;

    @RequestMapping(method = POST)
    @ResponseStatus(OK)
    public String getAllMessages(@RequestBody Topic topic) {

        StringBuilder record = new StringBuilder();

        record.append("[");

            for (Iterator i = (messageService.getAllMessages(topic.getTopic())).iterator(); i.hasNext(); ) {
                Object element = i.next();

                if (element instanceof GenericData.Record)
                    record = buildGenericDataRecord((GenericData.Record) element, record);

                if (i.hasNext())
                    record.append(",");

            }

        record.append("]");
        return record.toString();

    }


    private StringBuilder buildGenericDataRecord(GenericData.Record record, StringBuilder builder) {

        if (record.getSchema().getType() == Schema.Type.RECORD) {

            builder.append("{");

            record.getSchema().getFields()
                    .forEach(field -> {

                        builder.append("\"").append(field.name()).append("\" :");

                        if (record.get(field.name()) instanceof GenericData.Record) {
                            buildGenericDataRecord((GenericData.Record) record.get(field.pos()), builder);
                        } else {
                            builder.append("\"").append(record.get(field.name())).append("\"");
                        }

                        if (field.pos() != record.getSchema().getFields().size() - 1)
                            builder.append(",");

                    });
            builder.append("}");
        }

        return builder;
    }
}
