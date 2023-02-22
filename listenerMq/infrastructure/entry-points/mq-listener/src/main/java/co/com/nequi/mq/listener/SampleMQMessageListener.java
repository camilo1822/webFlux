package co.com.nequi.mq.listener;

import co.com.bancolombia.commons.jms.mq.MQListener;
import co.com.nequi.usecase.listener.ListenerUseCase;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SampleMQMessageListener {
    private final Timer timer = Metrics.timer("mq_receive_message", "operation", "my-operation"); // TODO: Change operation name
    private final ListenerUseCase listenerUseCase;

    // For fixed queues
    @MQListener(value = "DEV.QUEUE.1")
    public Mono<String> process(Message message) throws JMSException {
        // String corId = message.getJMSCorrelationID();
        String msgId = message.getJMSMessageID();
        timer.record(System.currentTimeMillis() - message.getJMSTimestamp(), TimeUnit.MILLISECONDS);
        String text = ((TextMessage) message).getText();
        return listenerUseCase.execute(msgId);
    }
}
