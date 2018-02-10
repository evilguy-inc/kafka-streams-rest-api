package com.evilguyinc.kafka.streams.rest.domain;

public class Topic {

    private String topic;
    private String serde;

    public Topic() {
    }

    public Topic(String topic, String serde) {
        this.topic = topic;
        this.serde = serde;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSerde() {
        return serde;
    }

    public void setSerde(String serde) {
        this.serde = serde;
    }
}
