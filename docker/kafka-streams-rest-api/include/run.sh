#!/usr/bin/env bash

chmod +x /var/kafka-streams-rest-api/kafka-streams-rest-api.jar

ln -s /var/kafka-streams-rest-api/kafka-streams-rest-api.jar /etc/init.d/kafka-streams-rest-api

rc-update add kafka-streams-rest-api default

rc-service kafka-streams-rest-api start

tail -f /dev/null