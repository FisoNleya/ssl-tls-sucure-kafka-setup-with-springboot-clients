package com.fiso.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {


    @KafkaListener(topics = "user_updates")
    public void receiveUserUpdate(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        log.info("Receiving user record update in Micro Service 3 : {} ", consumerRecord.value());

    }

}
