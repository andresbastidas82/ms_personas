package com.pragma.ms_personas.application.mapper;

import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.application.dto.PersonResponse;
import com.pragma.ms_personas.domain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPersonRequestMapper {

    Person toModel(PersonRequest personRequest);

    PersonResponse toResponse(Person person);
}
