package com.pragma.ms_personas.application.helper;

import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.application.dto.PersonResponse;
import reactor.core.publisher.Mono;

public interface IPersonHelper {

    Mono<PersonResponse> createPerson(PersonRequest personRequest);

    Mono<PersonResponse> findById(Long id);
}
