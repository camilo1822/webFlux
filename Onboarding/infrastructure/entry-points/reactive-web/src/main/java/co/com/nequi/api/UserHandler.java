package co.com.nequi.api;

import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
private  final UserUseCase userUseCase;
//private  final UseCase2 useCase2;
    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        Mono<String> user = userUseCase.getById(serverRequest.pathVariable("id"));
        //return ServerResponse.ok().bodyValue("Hola mundo"+user);
        return userUseCase.getById(serverRequest.pathVariable("id")).
                flatMap(x -> ServerResponse.ok().bodyValue(x));
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");

    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
