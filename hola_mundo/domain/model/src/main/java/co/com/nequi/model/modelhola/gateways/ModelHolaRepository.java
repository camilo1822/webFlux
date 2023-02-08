package co.com.nequi.model.modelhola.gateways;

import reactor.core.publisher.Mono;

public interface ModelHolaRepository {

    Mono<String> send(String message);
}
