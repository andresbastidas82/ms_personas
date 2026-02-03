package com.pragma.ms_personas.application.helper.impl;

import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.application.dto.PersonResponse;
import com.pragma.ms_personas.application.helper.IPersonHelper;
import com.pragma.ms_personas.application.mapper.IPersonRequestMapper;
import com.pragma.ms_personas.domain.api.IPersonServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonHelper implements IPersonHelper {

    private final IPersonServicePort personServicePort;
    private final IPersonRequestMapper personRequestMapper;

    @Override
    public Mono<PersonResponse> createPerson(PersonRequest personRequest) {
        return personServicePort.save(personRequestMapper.toModel(personRequest))
                .map(personRequestMapper::toResponse);
    }


}
