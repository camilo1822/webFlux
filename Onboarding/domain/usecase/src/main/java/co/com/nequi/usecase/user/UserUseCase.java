package co.com.nequi.usecase.user;

import co.com.nequi.model.user.gateways.FinacelRepository;
import co.com.nequi.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final FinacelRepository finacelRepository;

    public Mono<String> getById(String id){
        return userRepository.getById(id).flatMap(x -> finacelRepository.addMessage(x));
    }
}
