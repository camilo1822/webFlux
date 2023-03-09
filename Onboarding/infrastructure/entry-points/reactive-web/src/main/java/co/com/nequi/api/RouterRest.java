package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterRest {
@Bean
public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
    return route(GET("/api/v1/users/{id}"), handler::getById)
    .andRoute(POST("/api/v1/user"), handler::listenPOSTUseCase).and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));

    }
}
