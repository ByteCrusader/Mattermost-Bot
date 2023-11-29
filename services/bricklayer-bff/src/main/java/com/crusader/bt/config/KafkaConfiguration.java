package com.crusader.bt.config;

import com.crusader.bt.config.properties.KafkaProperties;
import com.crusader.bt.dto.BotDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
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
    public KafkaReceiver<String, BotDto> messageReceiver() {
        return KafkaReceiver.create(
                ReceiverOptions
                        .<String, BotDto>create(
                                buildProducerFactoryConfig(kafkaProperties)
                        )
                        .withValueDeserializer(new JsonDeserializer<>())
        );
    }

    @Bean
    public KafkaSender<String, BotDto> messageSender() {
        return KafkaSender.create(
                SenderOptions
                        .<String, BotDto>create(
                                buildProducerFactoryConfig(kafkaProperties)
                        )
                        .withValueSerializer(new JsonSerializer<>())
        );
    }

    private Map<String, Object> buildProducerFactoryConfig(KafkaProperties details) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, details.getBootstrap());
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
