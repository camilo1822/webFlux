package co.com.nequi.api;

import co.com.nequi.usecase.listener.ListenerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ListenerUseCase listenerUseCase;

    public Mono<ServerResponse> listenMessage(ServerRequest serverRequest) {
        System.out.println("listenerUseCase "+listenerUseCase);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(listenerUseCase.execute("asd"),String.class);
    }
}
