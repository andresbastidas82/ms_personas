package com.pragma.ms_personas.domain.usecase;

import com.pragma.ms_personas.domain.api.IPersonServicePort;
import com.pragma.ms_personas.domain.exception.BadRequestException;
import com.pragma.ms_personas.domain.model.Person;

import com.pragma.ms_personas.domain.spi.IPersonPersistencePort;
import com.pragma.ms_personas.domain.utils.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.pragma.ms_personas.domain.utils.Constants.EMAIL_IS_NOT_VALID;
import static com.pragma.ms_personas.domain.utils.Constants.EMAIL_IS_REQUIRED;
import static com.pragma.ms_personas.domain.utils.Constants.IDENTIFICATION_NUMBER_IS_REQUIRED;
import static com.pragma.ms_personas.domain.utils.Constants.NAME_IS_REQUIRED;

@Service
@RequiredArgsConstructor
public class PersonUseCase implements IPersonServicePort {

    private final IPersonPersistencePort personPersistencePort;

    @Override
    public Mono<Person> save(Person person) {
        return validateRules(person)
                .then(personPersistencePort.save(person));
    }

    private Mono<Void> validateRules(Person person) {
        List<String> errors = new ArrayList<>();
        if (person.getName() == null || person.getName().isEmpty()) {
            errors.add(NAME_IS_REQUIRED);
        }
        if(person.getEmail() == null || person.getEmail().isEmpty()) {
            errors.add(EMAIL_IS_REQUIRED);
        }
        if(!Validations.isValidEmail(person.getEmail())) {
            errors.add(EMAIL_IS_NOT_VALID);
        }
        if(person.getIdentificationNumber() == null || person.getIdentificationNumber().isEmpty()) {
            errors.add(IDENTIFICATION_NUMBER_IS_REQUIRED);
        }
        if (!errors.isEmpty()) {
            return Mono.error(new BadRequestException(String.join("|", errors)));
        }
        return Mono.empty();
    }
}
