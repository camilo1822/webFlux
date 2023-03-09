package co.com.nequi.finaclemessage;

import co.com.nequi.model.user.gateways.FinacelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FinacleAdapter implements FinacelRepository {
    @Override
    public Mono<String> addMessage(String message) {
        return Mono.just("Envio a finacle este mensaje "+message);
    }
}
