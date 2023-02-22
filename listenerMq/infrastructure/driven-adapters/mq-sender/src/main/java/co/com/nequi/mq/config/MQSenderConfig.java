package co.com.nequi.mq.config;

import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.mq.EnableMQMessageSender;
import co.com.nequi.model.modellistener.gateways.ModelListenerRepository;
import co.com.nequi.mq.sender.Sender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;

@Configuration
@EnableMQMessageSender
public class MQSenderConfig {

    @Bean
    public ModelListenerRepository modelGateway(MQMessageSender sender) throws JMSException {
        return new Sender(sender);
    }
}
