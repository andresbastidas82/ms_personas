package com.pragma.ms_personas.infrastructure.out.r2dbc.mapper;

import com.pragma.ms_personas.domain.model.Person;
import com.pragma.ms_personas.infrastructure.out.r2dbc.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPersonEntityMapper {

    PersonEntity toEntity(Person person);

    Person toModel(PersonEntity personEntity);
}
