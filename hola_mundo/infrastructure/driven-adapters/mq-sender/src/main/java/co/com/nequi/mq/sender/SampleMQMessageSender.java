package co.com.nequi.mq.sender;

import co.com.bancolombia.commons.jms.api.MQMessageSelectorListener;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.api.MQQueuesContainer;
import co.com.bancolombia.commons.jms.mq.EnableMQMessageSender;
import co.com.nequi.model.modelhola.gateways.ModelHolaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.jms.Destination;
import javax.jms.Message;

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

    // Enable it to retrieve a specific message by correlationId
//    public Mono<String> getResult(String correlationId) {
//        return listener.getMessage(correlationId)
//                .name("mq_receive_message")
//                .tag("operation", "my-operation") // TODO: Change operation name
//                .metrics()
//                .map(this::extractResponse);
//    }
//
//    private String extractResponse(Message message) {
//        TextMessage textMessage = (TextMessage) message;
//        return textMessage.getText();
//    }
}
