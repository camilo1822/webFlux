package co.com.nequi.model.user.gateways;

import reactor.core.publisher.Mono;

public interface FinacelRepository {

    Mono<String> addMessage(String message);
}
