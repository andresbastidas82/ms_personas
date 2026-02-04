package com.pragma.ms_personas.infrastructure.out.r2dbc.adapter;

import com.pragma.ms_personas.domain.model.Person;
import com.pragma.ms_personas.domain.spi.IPersonPersistencePort;
import com.pragma.ms_personas.infrastructure.out.r2dbc.mapper.IPersonEntityMapper;
import com.pragma.ms_personas.infrastructure.out.r2dbc.repository.IPersonR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonRepositoryAdapter implements IPersonPersistencePort {

    private final IPersonR2dbcRepository personR2dbcRepository;
    private final IPersonEntityMapper personEntityMapper;

    public PersonRepositoryAdapter(IPersonR2dbcRepository personR2dbcRepository, IPersonEntityMapper personEntityMapper) {
        this.personR2dbcRepository = personR2dbcRepository;
        this.personEntityMapper = personEntityMapper;
    }

    @Override
    public Mono<Person> save(Person person) {
        return personR2dbcRepository.save(personEntityMapper.toEntity(person))
                .map(personEntityMapper::toModel);
    }

    @Override
    public Mono<Person> findById(Long id) {
        return personR2dbcRepository.findById(id)
                .map(personEntityMapper::toModel);
    }
}
