package com.evilguyinc.kafka.streams.rest.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = {"com.evilguyinc.kafka.streams.rest.controller"})
public class ApplicationConfig {
}
