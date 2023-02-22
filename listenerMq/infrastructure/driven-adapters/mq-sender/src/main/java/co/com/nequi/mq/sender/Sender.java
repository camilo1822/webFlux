package co.com.nequi.mq.sender;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.nequi.model.modellistener.gateways.ModelListenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.jms.Message;

@RequiredArgsConstructor
@Slf4j
public class Sender implements ModelListenerRepository {

  private final MQMessageSender sender;

    @Override
    public Mono<String> send(String corId) {
            return sender.send(context ->
                    {
                        Message textMessage;
                        textMessage = context.createTextMessage("Respuesta exitosa");
                        textMessage.setJMSCorrelationID(corId);
                        return textMessage;
                    }).doOnError(i -> System.out.println("error" +i.getMessage()))
                    .doOnSuccess(i -> System.out.println("success"))
                    .name("mq_send_message")
                    .tag("operation", "my-operation")
                    .metrics();
    }

}
