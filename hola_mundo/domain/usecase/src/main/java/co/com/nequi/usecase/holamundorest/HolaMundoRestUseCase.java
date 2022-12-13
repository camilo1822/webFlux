package co.com.nequi.usecase.holamundorest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class HolaMundoRestUseCase {

    public Mono<String> execute(String request) {
        return Mono.just("hola mundo");
    }
}
