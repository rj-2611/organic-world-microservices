package com.stackroute.orderservice.service;

import com.stackroute.orderservice.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageProducerService class should be used to send messages to Kafka Topic
 * **TODO**
 * This class should use KafkaTemplate to send OrderDto message
 */
@Service
public class MessageProducerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerService.class);

    @Value("${kafka.topic-name}")
    private String topic;

    /**
     * **TODO**
     * Inject a bean of KafkaTemplate created in KafkaConfig class
     */
    @Autowired
    private KafkaTemplate<String, OrderDto> kafkaTemplate;
    /**
     * **TODO**
     * Create a method sendOrderMessage(OrderDto message)
     * to send order details message to Kafka topic
     */
    public String sendOrderMessage(OrderDto message){
        kafkaTemplate.send(topic, message);
        return "Success";
    }

}