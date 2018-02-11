Kafka Streams REST api
======================
The Kafka Streams REST api provides a rest end points for simple topic content viewing. 
Essentially it is an analogue for [Kafka REST Proxy](https://github.com/confluentinc/kafka-rest), 
but in the combination of [Kafka Topics ui](https://github.com/Landoop/kafka-topics-ui) 
it was really hard to configure to fetch full topics for development or at least fragments of it. 
I tried to adjust timeouts, buffer size and nothing worked out so well to track the topic.

Running in docker
-----------------
You can pull down and run it locally by simply executing (don't forget to define correct network of your services):
```bash
    docker run --rm -p 9096:9096 --network=compose_default \
        -e "RUN_ARGS=--kafka.bootstrap.servers=broker:9092 --kafka.zookeeper.connect=zookeeper:2181 --kafka.schema.registry.url=http://schema-registry:8081" \
        -d kafka-streams-rest-api:latest
```

Single line:
```bash
    docker run --rm -p 9096:9096 --network=compose_default  -e "RUN_ARGS=--kafka.bootstrap.servers=broker:9092 --kafka.zookeeper.connect=zookeeper:2181 --kafka.schema.registry.url=http://schema-registry:8081"  -d evilguy/kafka-streams-rest-api:latest
```

Docker compose usage example:
```yaml
# Kafka Streams REST api
  kafka-streams-rest-api:
    image: evilguy/kafka-streams-rest-api:latest
    hostname: kafka-streams-rest-api
    container_name: kafka-streams-rest-api
    depends_on:
      - zookeeper
      - broker
      - schema-registry
    ports:
      - 9096:9096
    environment:
      RUN_ARGS: --kafka.bootstrap.servers=broker:9092 --kafka.zookeeper.connect=zookeeper:2181 --kafka.schema.registry.url=http://schema-registry:8081
```

Build from source
-----------------
This is a quick build from source, assuming that you have everything running locally, 
if not update [application.yml](src/main/resources/application.yml)

```bash
    git clone https://github.com/evilguy-inc/kafka-streams-rest-api.git
    cd kafka-streams-rest-api
    mvn spring-boot:run
```


Quickstart
----------

Get all topics that user is viewing:
```
curl -X GET http://localhost:9096/api/topic
```

Get all topics in kafka:
```
curl -X GET 'http://localhost:9096/api/topic/kafka?all=true'
```

Post topic for user viewing
```
curl -X POST http://localhost:9096/api/topic \
  -H 'Content-Type: application/json' \
  -d '{
  "topic": "topic.name.from.kafka"
}'
```

Get all messages from kafka:
```
curl -X GET  'http://localhost:9096/api/message/all?topic=topic.name.from.kafka' \
  -H 'Content-Type: application/json'
```

Sometimes getting all messages might be very heavy operation, in that case use range of messages:
```
curl -X GET \
  'http://localhost:9096/api/message/period?topic=topic.name.from.kafka&start=0&lenght=100' \
  -H 'Content-Type: application/json' 
```

Find message by message id:
```
curl -X GET \
  'http://localhost:9096/api/message?topic=topic.name.from.kafka&key={"Id":2135}' \
  -H 'Content-Type: application/json'
```


Support/Development
-------------------

#### Creating a docker image

1. Build an artifact:
```bash
    mvn clean package -Pprepare-for-docker
```

2. build docker image
```bash
    docker build --rm -f docker/kafka-streams-rest-api/Dockerfile -t kafka-streams-rest-api:latest docker/kafka-streams-rest-api
```

3. publish to docker
```bash
    docker tag kafka-streams-rest-api:latest evilguy/kafka-streams-rest-api:0.0.1
    docker tag kafka-streams-rest-api:latest evilguy/kafka-streams-rest-api:latest
    docker push evilguy/kafka-streams-rest-api:0.0.1
    docker push evilguy/kafka-streams-rest-api:latest
```