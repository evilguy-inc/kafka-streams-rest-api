FROM ibmjava:8-sdk-alpine

RUN apk --update add bash openrc --no-cache

COPY include/kafka-streams-rest-api.jar /var/kafka-streams-rest-api/kafka-streams-rest-api.jar
COPY include/kafka-streams-rest-api.conf /var/kafka-streams-rest-api/kafka-streams-rest-api.conf
COPY include/run.sh /run.sh

EXPOSE 9096

CMD /run.sh 
