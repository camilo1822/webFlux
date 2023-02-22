package co.com.nequi.usecase.holamundorest;

import co.com.nequi.model.modelhola.ModelHola;
import co.com.nequi.model.modelhola.gateways.ModelHolaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class HolaMundoRestUseCase {

    private final ModelHolaRepository modelGateway;
    public Mono<String> execute(String request) {
         return modelGateway.send("soy un  mq")
                 .flatMap(modelGateway::listen);
    }
}
