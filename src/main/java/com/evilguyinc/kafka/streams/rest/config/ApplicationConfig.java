package com.evilguyinc.kafka.streams.rest.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = {
        "com.evilguyinc.kafka.streams.rest.properties",
        "com.evilguyinc.kafka.streams.rest.controller",
        "com.evilguyinc.kafka.streams.rest.service"
})
public class ApplicationConfig {
}
