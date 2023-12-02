package com.crusader.bt.message;

import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.enums.MessageEventType;
import com.crusader.bt.service.ClientService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import static com.crusader.bt.message.MessageProducer.EVENT_TYPE_HEADER;

@Slf4j(topic = "Consumer-MQ")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true")
public class MessageConsumer {

    private final KafkaReceiver<String, MessageDto> bffMessageReceiver;
    private final ClientService clientService;

    @SneakyThrows
    @PostConstruct
    public void receivedBffMessage() {

        bffMessageReceiver
                .receive()
                .map(r -> {
                    log.info("Received message: " + r);
                    return r;
                })
                .flatMap(this::processMessage)
                .doOnNext(recordsMap ->
                        recordsMap.receiverOffset().acknowledge()
                )
                .onErrorContinue(
                        exc -> !(exc instanceof NoTransactionException),
                        (exc, val) -> log.info(
                                "Event dropped. Application exception into customer queue consumer : {} - {} ",
                                exc.toString(),
                                exc.getLocalizedMessage()
                        )
                )
                .retryWhen(
                        Retry.indefinitely()
                                .doBeforeRetry(s -> log.debug("Retrying after kafka exception", s.failure()))
                                .filter(KafkaException.class::isInstance)
                )
                .subscribe();
    }

    private Mono<ReceiverRecord<String, MessageDto>> processMessage(ReceiverRecord<String, MessageDto> receiverRecord) {

        String eventType = new String(receiverRecord.headers().lastHeader(EVENT_TYPE_HEADER).value());

        if (MessageEventType.COMPLETE_CREATE_BOT_EVENT.getName().equals(eventType)) {
            return clientService.successCreateBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.FAIL_CREATE_BOT_EVENT.getName().equals(eventType)) {
            return clientService.failedCreateBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.COMPLETE_EDIT_BOT_EVENT.getName().equals(eventType)) {
            return clientService.successUpdateBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.FAIL_EDIT_BOT_EVENT.getName().equals(eventType)) {
            return clientService.failedUpdateBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.COMPLETE_DELETE_BOT_EVENT.getName().equals(eventType)) {
            return clientService.successDeleteBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.FAIL_DELETE_BOT_EVENT.getName().equals(eventType)) {
            return clientService.failedDeleteBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else {
            return Mono.just(receiverRecord);
        }
    }

}
