package com.pragma.ms_personas.domain.spi;

import com.pragma.ms_personas.domain.model.Person;
import reactor.core.publisher.Mono;

public interface IPersonPersistencePort {

    Mono<Person> save(Person person);

    Mono<Person> findById(Long id);
}
