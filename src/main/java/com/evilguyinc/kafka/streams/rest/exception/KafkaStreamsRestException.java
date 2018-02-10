package com.evilguyinc.kafka.streams.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class KafkaStreamsRestException extends RuntimeException{

    public KafkaStreamsRestException() {
        super();
    }

    public KafkaStreamsRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaStreamsRestException(String message) {
        super(message);
    }

    public KafkaStreamsRestException(Throwable cause) {
        super(cause);
    }
}
