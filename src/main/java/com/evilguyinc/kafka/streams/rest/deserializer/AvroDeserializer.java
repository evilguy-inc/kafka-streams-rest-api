package com.evilguyinc.kafka.streams.rest.deserializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

import java.util.Iterator;
import java.util.List;

public class AvroDeserializer {

    public String deserilze(List<Object> messages){
        StringBuilder record = new StringBuilder();

        record.append("[");

        for (Iterator i = messages.iterator(); i.hasNext(); ) {
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
