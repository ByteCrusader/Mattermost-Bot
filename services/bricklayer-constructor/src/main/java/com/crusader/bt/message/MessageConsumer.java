package com.crusader.bt.message;

import com.crusader.bt.dto.MessageDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.util.retry.Retry;

@Slf4j(topic = "Consumer-MQ")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true")
public class MessageConsumer {

    private final KafkaReceiver<String, MessageDto> constructorMessageReceiver;

    @SneakyThrows
    @PostConstruct
    public void receivedConstructorMessage() {

        constructorMessageReceiver
                .receive()
                .map(r -> {
                    log.info("Received message: " + r);
                    return r;
                })
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
}
