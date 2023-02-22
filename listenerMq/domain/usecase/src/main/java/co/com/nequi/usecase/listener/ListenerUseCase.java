package co.com.nequi.usecase.listener;

import co.com.nequi.model.modellistener.gateways.ModelListenerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ListenerUseCase {

    private final ModelListenerRepository modelListenerRepository;
    public Mono<String> execute(String corId) {
          modelListenerRepository.send(corId)
                 .doOnSuccess(i -> System.out.println("caso de usao "+i))
                 .doOnError(i -> System.out.println("Error "+ i)).subscribe();
         return Mono.just("response");
    }
}
