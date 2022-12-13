package co.com.nequi.api;

import co.com.nequi.usecase.holamundorest.HolaMundoRestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

private final HolaMundoRestUseCase holaMundoRestUseCase;


    public Mono<ServerResponse> holaMundo(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(holaMundoRestUseCase.execute("Hola"),String.class);
    }
}
