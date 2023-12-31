package com.crusader.bt.message;

import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.enums.MessageEventType;
import com.crusader.bt.service.BotsService;
import com.crusader.bt.utils.MqUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
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

    private final KafkaReceiver<String, MessageDto> storageMessageReceiver;
    private final BotsService botsService;

    @SneakyThrows
    @PostConstruct
    public void receivedStorageMessage() {

        storageMessageReceiver
                .receive()
                .flatMap(this::processMessage)
                .doOnNext(recordsMap ->
                        recordsMap.receiverOffset().acknowledge()
                )
                .onErrorContinue(
                        MqUtil::errorPredicate,
                        (exc, val) -> log.info(
                                "Event dropped. Application exception into storage queue consumer : {} - {} ",
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

        if (MessageEventType.PROCESSING_CREATE_BOT_EVENT.getName().equals(eventType)) {
            return botsService.createBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.PROCESSING_EDIT_BOT_EVENT.getName().equals(eventType)) {
            return botsService.updateBotInfo(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else if (MessageEventType.PROCESSING_DELETE_BOT_EVENT.getName().equals(eventType)) {
            return botsService.deleteBot(receiverRecord.value())
                    .thenReturn(receiverRecord);
        } else {
            return Mono.just(receiverRecord);
        }
    }

}
