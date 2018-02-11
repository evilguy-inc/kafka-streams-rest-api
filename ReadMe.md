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


Docker compose usage example:
```yaml

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



Support/Development
-------------------

#### Creating a docker container

1. Build an artifact:
```bash
    mvn clean package -Pprepare-for-docker
```

2. build docker container
```bash
    docker build --rm -f docker/kafka-streams-rest-api/Dockerfile -t kafka-streams-rest-api:latest docker/kafka-streams-rest-api
```

3. publish to docker
```bash
    docker tag kafka-streams-rest-api:latest evilguy/kafka-streams-rest-api:0.0.1
    docker push evilguy/kafka-streams-rest-api:0.0.1
```