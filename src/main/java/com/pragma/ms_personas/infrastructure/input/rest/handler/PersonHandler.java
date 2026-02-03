package com.pragma.ms_personas.infrastructure.input.rest.handler;


import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.application.helper.IPersonHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PersonHandler {
    private final IPersonHelper personHelper;

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return request.bodyToMono(PersonRequest.class)
                .flatMap(personHelper::createPerson)
                .flatMap(personResponse ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(personResponse)
                );
    }
}
