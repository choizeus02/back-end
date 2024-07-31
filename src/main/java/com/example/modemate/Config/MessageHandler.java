package com.example.modemate.Config;

import com.example.modemate.DTO.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class MessageHandler {

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @MessageMapping("/message")
    public void greeting(ChatMessage message) throws Exception {
        message.setCreateAt(LocalDateTime.now());
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
    }
}