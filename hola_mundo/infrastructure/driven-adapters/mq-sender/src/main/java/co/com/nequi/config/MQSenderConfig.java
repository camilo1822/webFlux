package co.com.nequi.config;

import co.com.bancolombia.commons.jms.api.MQMessageSelectorListener;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.api.MQQueueCustomizer;
import co.com.bancolombia.commons.jms.mq.EnableMQMessageSender;
import co.com.bancolombia.commons.jms.mq.EnableMQSelectorMessageListener;
import co.com.nequi.model.modelhola.gateways.ModelHolaRepository;
import co.com.nequi.mq.sender.SampleMQMessageSender;
import com.ibm.mq.jms.MQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;

@Configuration
@EnableMQMessageSender
@EnableMQSelectorMessageListener
public class MQSenderConfig {

    @Bean
    public ModelHolaRepository modelGateway(@Value("${ibm.mq.listener_timeout}") Long listenerTimeout,
                                            MQMessageSender sender,
                                            @Value("${commons.jms.output-queue}") String outputQueue,
                                            @Value("${commons.jms.input-queue}") String inputQueue,
                                            MQQueueCustomizer customizer,
                                            MQMessageSelectorListener listener) throws JMSException {

        Destination destinationQueue = createDestinationQueue(outputQueue, customizer);
        Destination inputDestinationQueue = createDestinationQueue(inputQueue, customizer);
        return new SampleMQMessageSender(listenerTimeout,inputDestinationQueue,sender,listener,destinationQueue);
    }

    private Destination createDestinationQueue(String outputQueue, MQQueueCustomizer customizer) throws JMSException {
        Queue queue = new MQQueue(outputQueue);
        customizer.customize(queue);
        return queue;
    }
}
