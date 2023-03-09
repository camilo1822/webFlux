package co.com.nequi.memdatabase;

import co.com.nequi.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class UserMemoryAdapter implements UserRepository {

    private ConcurrentHashMap<String, String> database = new ConcurrentHashMap<>();
    @Override
    public Mono<String> getById(String id) {
        return Mono.just("My id: "+id);
        //return database.get(id);
    }
}
