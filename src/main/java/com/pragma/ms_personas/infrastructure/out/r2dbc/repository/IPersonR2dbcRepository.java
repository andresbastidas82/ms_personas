package com.pragma.ms_personas.infrastructure.out.r2dbc.repository;

import com.pragma.ms_personas.infrastructure.out.r2dbc.entity.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IPersonR2dbcRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}
