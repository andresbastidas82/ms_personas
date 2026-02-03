package com.pragma.ms_personas.infrastructure.input.rest.router;

import com.pragma.ms_personas.infrastructure.input.rest.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> personRoutes(PersonHandler personHandler) {
        return route(POST("/api/v1/person"), personHandler::createPerson);
    }
}
