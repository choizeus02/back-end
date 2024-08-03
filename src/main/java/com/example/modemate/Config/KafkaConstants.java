package com.example.modemate.Config;

import org.springframework.beans.factory.annotation.Value;

public class KafkaConstants {

//    @Value("${kafka.broker}")
//    public static String KAFKA_BROKER;
    public static final String KAFKA_TOPIC = "kafka-chat";
    public static final String GROUP_ID = "foo";

    public static final String KAFKA_BROKER = "kafka:9092";
}