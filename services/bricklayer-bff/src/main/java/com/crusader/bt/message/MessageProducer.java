package com.crusader.bt.message;

import com.crusader.bt.config.properties.KafkaProperties;
import com.crusader.bt.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.nio.charset.StandardCharsets;

@Slf4j(topic = "Producer-MQ")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true")
public class MessageProducer {

    private final KafkaSender<String, MessageDto> constructorMessageSender;
    private final KafkaProperties properties;

    @SneakyThrows
    public void sendConstructorMessage(MessageDto message, String source) {
        ProducerRecord<String, MessageDto> producerRecord = new ProducerRecord<>(
                properties.getConstructorQueue().getTopic(),
                message
        );

        addHeader(producerRecord, "BFF", source);

        Mono<SenderRecord<String, MessageDto, Object>> senderRecord = Mono
                .just(SenderRecord.create(producerRecord, null))
                .doOnNext(sendRecord ->
                        log.info(
                                "Send message: {} \n into topic {}, with key {} and source {}",
                                sendRecord.value(),
                                sendRecord.topic(),
                                sendRecord.key(),
                                source
                        )
                );

        sendConstructorMessage(senderRecord);
    }

    private void addHeader(ProducerRecord<String, MessageDto> producerRecord, String key, String value) {
        producerRecord.headers().add(key, value.getBytes(StandardCharsets.UTF_8));
    }

    private void sendConstructorMessage(Mono<SenderRecord<String, MessageDto, Object>> senderRecord) {

        constructorMessageSender.send(senderRecord)
                .single()
                .doOnSuccess(senderResult -> log.info(
                        "Message sent, offset : {}",
                        senderResult.recordMetadata().offset()
                ))
                .doOnError(ex -> log.error(
                        "Message sending error: {}", ex.getMessage(), ex)
                )
                .subscribe();
    }
}
