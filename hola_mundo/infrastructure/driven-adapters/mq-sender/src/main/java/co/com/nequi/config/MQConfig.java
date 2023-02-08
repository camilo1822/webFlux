package co.com.nequi.config;

import co.com.bancolombia.commons.jms.api.MQProducerCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.jms.DeliveryMode;

@Slf4j
@Configuration
public class MQConfig {
    @Bean
    @Primary
    public MQProducerCustomizer mqProducerCustomizer() {
        return producer -> producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
}
