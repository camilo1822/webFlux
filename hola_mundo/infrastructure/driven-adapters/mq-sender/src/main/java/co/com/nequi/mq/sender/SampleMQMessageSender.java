package co.com.nequi.mq.sender;

import co.com.bancolombia.commons.jms.api.MQMessageSelectorListener;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.api.MQQueuesContainer;
import co.com.nequi.model.modelhola.gateways.ModelHolaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@RequiredArgsConstructor
@Slf4j
public class SampleMQMessageSender  implements ModelHolaRepository {
    private final Long timeout;
    private final Destination inputDestination;
    private final MQMessageSender sender;
    private final MQMessageSelectorListener listener;
    private final Destination destinationQueue;
    @Override
    public Mono<String> send(String message) {
        return sender.send(destinationQueue,context ->
                {
                    Message textMessage;
                    textMessage = context.createTextMessage(message);
                    return textMessage;
                })
                .name("mq_send_message")
                .tag("operation", "my-operation")
                .metrics();
    }

    @Override
    public Mono<String> listen(String message) {
        return listener
                .getMessage(message,timeout,inputDestination)
                .map(this::extractResponse).doOnSuccess(i -> System.out.println("success"))
                .doOnError(i -> System.out.println("error "+i.getMessage()));
    }

    private String extractResponse(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            return textMessage.getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
