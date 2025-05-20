package com.fiso.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserProducer {


    private final KafkaTemplate<String, UserDTO> userUpdateKafkaTemplate;


    @Value("${topic.user.update}")
    private String userUpdateTopic;



    public void notifyOnUserUpdate(UserDTO userDTO){

        final ProducerRecord<String, UserDTO> producerRecord = new ProducerRecord<>(userUpdateTopic,
                userDTO);
        userUpdateKafkaTemplate.send(producerRecord);
        log.info("Published user details {}", userDTO);

    }



}
