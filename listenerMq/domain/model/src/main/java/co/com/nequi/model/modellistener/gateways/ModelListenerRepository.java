package co.com.nequi.model.modellistener.gateways;

import reactor.core.publisher.Mono;

public interface ModelListenerRepository {

    Mono<String> send(String corId);

}
