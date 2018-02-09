package com.evilguyinc.kafka.streams.rest.domain;

public class Topic {

    private String topic;

    public Topic() {
    }

    public Topic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
