package co.com.nequi.model.user.gateways;

import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<String> getById(String id);
}
