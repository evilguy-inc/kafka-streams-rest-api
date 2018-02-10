Kafka Streams REST api
======================
The Kafka Streams REST api provides a rest end points for simple topic content viewing. 
Essentially it is an analogue for [Kafka REST Proxy](https://github.com/confluentinc/kafka-rest), 
but in the combination of [Kafka Topics ui](https://github.com/Landoop/kafka-topics-ui) 
it was really hard to configure to fetch full topics for development or at least fragments of it. 
I tried to adjust timeouts, buffer size and nothing worked out so well to track the topic.

Running in docker
-----------------

Build from source
-----------------
This is a quick build from source, assuming that you have everything running locally, 
if not update [application.yml](src/main/resources/application.yml)

```
    git clone https://github.com/evilguy-inc/kafka-streams-rest-api.git
    cd kafka-streams-rest-api
    mvn spring-boot:run
```


Quickstart
----------