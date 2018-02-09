package com.evilguyinc.kafka.streams.rest.properties;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class StreamProperties {

    private static final String KAFKA_PROPERTY_PREFIX = "kafka";

    @Autowired
    private ConfigurableEnvironment environment;

    public Properties getStreamProperties(){
        Properties properties = new Properties();

        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

        getAllProperties(this.environment, KAFKA_PROPERTY_PREFIX)
                .forEach(properties::put);


        properties.put("application.id", "kafka-streams-rest-api" + LocalDateTime.now());

        return properties;
    }


    /**
     * Spring doesn't allow to import properties as key value pairs
     * <br/>
     *
     * @param environment is Autowired property
     * @return a map of all defined properties
     * @see <a href="https://jira.spring.io/browse/SPR-10241">https://jira.spring.io/browse/SPR-10241</a>
     */
    private static Map<String, Object> getAllProperties(ConfigurableEnvironment environment, String keyPrefix) {
        Map<String, Object> properties = new HashMap<>();

        environment.getPropertySources()
                .forEach(propertySource -> {
                    if (propertySource instanceof EnumerablePropertySource) {
                        for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                            if (key.startsWith(keyPrefix)) {
                                // excludes '.' character from property subkey
                                String subKey = StringUtils.isEmpty(keyPrefix) ? key : key.substring(keyPrefix.length() + 1);

                                if (!properties.containsKey(subKey))
                                    properties.put(subKey, propertySource.getProperty(key));
                            }

                        }
                    }
                });

        return properties;
    }
}
