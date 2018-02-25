package com.evilguyinc.kafka.streams.rest.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.avro.generic.GenericData;
import org.springframework.beans.factory.annotation.Autowired;

public class AvroJsonConverter {


    @Autowired
    private ObjectMapper objectMapper;


    public ObjectNode getJsonFrom(GenericData.Record record) {
        return create(record);
    }



    private ObjectNode create(GenericData.Record record) {
        ObjectNode node = objectMapper.createObjectNode();

        for (int i = 0; i < record.getSchema().getFields().size(); i++) {


            if (record.get(i) instanceof GenericData.Record) {

                node.set(record.getSchema().getFields().get(i).name(),
                        create((GenericData.Record) record.get(i)));

            } else {
                node.put(record.getSchema().getFields().get(i).name(),
                        String.valueOf(record.get(i)));
            }

        }

        return node;
    }


}
