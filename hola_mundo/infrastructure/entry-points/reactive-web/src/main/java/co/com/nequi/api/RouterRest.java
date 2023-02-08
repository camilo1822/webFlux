package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRest {

    private static final String HOLA_MUNDO_ENDPOINT_V1 = "/api/prueba/holaMundo";
    private static final String CREDITS_GET_PRODUCT_ENDPOINT_V1 = "/api/prueba/getProduct";
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {

        return route(POST(HOLA_MUNDO_ENDPOINT_V1), handler::holaMundo).andRoute(POST(CREDITS_GET_PRODUCT_ENDPOINT_V1), handler::holaMundo);
    }
}
