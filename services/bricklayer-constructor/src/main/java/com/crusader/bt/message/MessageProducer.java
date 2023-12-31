package com.crusader.bt.message;

import com.crusader.bt.config.properties.KafkaProperties;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.enums.MessageEventType;
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
import java.time.OffsetDateTime;

@Slf4j(topic = "Producer-MQ")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true")
public class MessageProducer {

    public static final String EVENT_TIME_HEADER = "eventTime";
    public static final String EVENT_TYPE_HEADER = "eventType";
    public static final String SOURCE_HEADER = "source";
    public static final String SOURCE_VALUE = "constructor.application";

    private final KafkaSender<String, MessageDto> genericMessageSender;
    private final KafkaProperties properties;

    @SneakyThrows
    public Mono<Void> sendGenericMessage(MessageDto message, MessageEventType eventType) {
        return sendRequestMessage(
                genericMessageSender,
                message,
                properties.getBricksQueue().getTopic(),
                eventType
        );
    }

    private Mono<Void> sendRequestMessage(KafkaSender<String, MessageDto> messageSender,
                                          MessageDto message,
                                          String topic,
                                          MessageEventType eventType) {
        ProducerRecord<String, MessageDto> producerRecord = new ProducerRecord<>(
                topic,
                message.getUsername(),
                message
        );

        addHeader(producerRecord, EVENT_TIME_HEADER, OffsetDateTime.now().toString());
        addHeader(producerRecord, EVENT_TYPE_HEADER, eventType.getName());
        addHeader(producerRecord, SOURCE_HEADER, SOURCE_VALUE);

        Mono<SenderRecord<String, MessageDto, Object>> senderRecord = Mono
                .just(SenderRecord.create(producerRecord, null))
                .doOnNext(sendRecord ->
                        log.info(
                                "Send message: {} \n into topic {}, with key {} and source {}",
                                sendRecord.value(),
                                sendRecord.topic(),
                                sendRecord.key(),
                                eventType
                        )
                );

        return sendSingleMessage(messageSender, senderRecord);
    }

    private void addHeader(ProducerRecord<String, MessageDto> producerRecord, String key, String value) {
        producerRecord.headers().add(key, value.getBytes(StandardCharsets.UTF_8));
    }

    private Mono<Void> sendSingleMessage(KafkaSender<String, MessageDto> messageSender,
                                         Mono<SenderRecord<String, MessageDto, Object>> senderRecord) {

        return messageSender.send(senderRecord)
                .single()
                .doOnSuccess(senderResult -> log.info(
                        "Message sent, offset : {}",
                        senderResult.recordMetadata().offset()
                ))
                .doOnError(ex -> log.error(
                        "Message sending error: {}", ex.getMessage(), ex)
                )
                .then();
    }
}
