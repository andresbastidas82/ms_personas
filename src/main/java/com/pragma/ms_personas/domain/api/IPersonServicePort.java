package com.pragma.ms_personas.domain.api;

import com.pragma.ms_personas.domain.model.Person;
import reactor.core.publisher.Mono;

public interface IPersonServicePort {

    Mono<Person> save(Person person);
}
