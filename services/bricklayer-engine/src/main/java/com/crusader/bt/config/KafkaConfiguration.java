package com.crusader.bt.config;

import com.crusader.bt.config.properties.KafkaProperties;
import com.crusader.bt.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "MQ-Kafka")
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {KafkaProperties.class})
public class KafkaConfiguration {

    private static final String SASL_JAAS_CONFIG_TEMPLATE = "%s required username=\"%s\" password=\"%s\";";

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaReceiver<String, MessageDto> engineMessageReceiver() {
        return KafkaReceiver.create(
                ReceiverOptions
                        .<String, MessageDto>create(
                                buildConsumerFactoryConfig(kafkaProperties)
                        )
                        .withValueDeserializer(new JsonDeserializer<>(MessageDto.class))
                        .subscription(Collections.singleton(kafkaProperties.getEngineQueue().getTopic()))
        );
    }

    @Bean
    public KafkaSender<String, MessageDto> constructorMessageSender() {
        return KafkaSender.create(
                SenderOptions
                        .create(
                                buildProducerFactoryConfig(kafkaProperties)
                        )
        );
    }

    private Map<String, Object> buildConsumerFactoryConfig(KafkaProperties details) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, details.getBootstrap());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, details.getEngineQueue().getClientNGroupId());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, details.getEngineQueue().getClientNGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, details.getSessionTimeoutMs());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, details.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, details.getMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, details.getMaxPollIntervalMs());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, details.getOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        fillSecurity(details, props);
        return props;
    }

    private Map<String, Object> buildProducerFactoryConfig(KafkaProperties details) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, details.getBootstrap());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, details.getConstructorQueue().getClientNGroupId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        fillSecurity(details, props);
        return props;
    }

    private void fillSecurity(KafkaProperties details, Map<String, Object> props) {
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, details.getSecurityProtocol());
        props.put(SaslConfigs.SASL_MECHANISM, details.getSaslMechanism());
        props.put(
                SaslConfigs.SASL_JAAS_CONFIG,
                String.format(
                        SASL_JAAS_CONFIG_TEMPLATE,
                        PlainLoginModule.class.getName(),
                        details.getSaslJaasUsername(),
                        details.getSaslJaasPassword()
                )
        );
    }
}
