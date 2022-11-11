package com.bu.softwareengineering.contest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumerService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "new-contest-topic", groupId = "contest-group")
    public void consume(String message) throws JsonProcessingException {
        Map messageToWs = new HashMap();
        messageToWs.put("content", message);
        simpMessagingTemplate.convertAndSend("/topic/newContest",message);
        System.out.println("Kafka received message: "+ message);
    }
}
