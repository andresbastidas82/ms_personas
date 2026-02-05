package com.pragma.ms_personas.domain.usecase;

import com.pragma.ms_personas.domain.exception.BadRequestException;
import com.pragma.ms_personas.domain.exception.NotFoundException;
import com.pragma.ms_personas.domain.model.Person;
import com.pragma.ms_personas.domain.spi.IPersonPersistencePort;
import com.pragma.ms_personas.domain.utils.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonUseCaseTest {

    @Mock
    private IPersonPersistencePort personPersistencePort;

    @InjectMocks
    private PersonUseCase personUseCase;

    // --- TESTS PARA SAVE (Happy Path) ---

    @Test
    @DisplayName("Save: Should save person when all data is valid")
    void save_WhenValid_ShouldReturnSavedPerson() {
        // Arrange
        Person person = new Person();
        person.setName("Juan Perez");
        person.setEmail("juan.perez@example.com");
        person.setIdentificationNumber("123456789");

        when(personPersistencePort.save(person)).thenReturn(Mono.just(person));

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectNext(person)
                .verifyComplete();

        verify(personPersistencePort).save(person);
    }

    // --- TESTS PARA SAVE (Validaciones / BadRequestException) ---

    @Test
    @DisplayName("Save: Should throw BadRequestException when Name is null/empty")
    void save_WhenNameInvalid_ShouldThrowBadRequest() {
        // Arrange
        Person person = new Person();
        person.setName(""); // Inválido
        person.setEmail("valid@email.com");
        person.setIdentificationNumber("123");

        // IMPORTANTE: Mockear save() para evitar NullPointerException por la evaluación eager de .then()
        when(personPersistencePort.save(any(Person.class))).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException
                        && throwable.getMessage().contains(Constants.NAME_IS_REQUIRED))
                .verify();
    }

    @Test
    @DisplayName("Save: Should throw BadRequestException when Email is null/empty")
    void save_WhenEmailEmpty_ShouldThrowBadRequest() {
        // Arrange
        Person person = new Person();
        person.setName("Juan");
        person.setEmail(null); // Inválido
        person.setIdentificationNumber("123");

        when(personPersistencePort.save(any(Person.class))).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException
                        && throwable.getMessage().contains(Constants.EMAIL_IS_REQUIRED))
                .verify();
    }

    @Test
    @DisplayName("Save: Should throw BadRequestException when Email format is invalid")
    void save_WhenEmailFormatInvalid_ShouldThrowBadRequest() {
        // Arrange
        Person person = new Person();
        person.setName("Juan");
        person.setEmail("correo-no-valido"); // Formato inválido
        person.setIdentificationNumber("123");

        when(personPersistencePort.save(any(Person.class))).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException
                        && throwable.getMessage().contains(Constants.EMAIL_IS_NOT_VALID))
                .verify();
    }

    @Test
    @DisplayName("Save: Should throw BadRequestException when Identification Number is null/empty")
    void save_WhenIdentificationInvalid_ShouldThrowBadRequest() {
        // Arrange
        Person person = new Person();
        person.setName("Juan");
        person.setEmail("juan@test.com");
        person.setIdentificationNumber(""); // Inválido

        when(personPersistencePort.save(any(Person.class))).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException
                        && throwable.getMessage().contains(Constants.IDENTIFICATION_NUMBER_IS_REQUIRED))
                .verify();
    }

    @Test
    @DisplayName("Save: Should combine multiple error messages")
    void save_WhenMultipleFieldsInvalid_ShouldCombineErrors() {
        // Arrange
        Person person = new Person();
        person.setName(""); // Error 1
        person.setEmail(""); // Error 2
        person.setIdentificationNumber(""); // Error 3

        when(personPersistencePort.save(any(Person.class))).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.save(person);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException
                        && throwable.getMessage().contains(Constants.NAME_IS_REQUIRED)
                        && throwable.getMessage().contains(Constants.EMAIL_IS_REQUIRED)
                        && throwable.getMessage().contains(Constants.IDENTIFICATION_NUMBER_IS_REQUIRED))
                .verify();
    }

    // --- TESTS PARA FINDBYID ---

    @Test
    @DisplayName("FindById: Should return person when found")
    void findById_WhenFound_ShouldReturnPerson() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setName("Juan");

        when(personPersistencePort.findById(id)).thenReturn(Mono.just(person));

        // Act
        Mono<Person> result = personUseCase.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(person)
                .verifyComplete();

        verify(personPersistencePort).findById(id);
    }

    @Test
    @DisplayName("FindById: Should throw NotFoundException when empty")
    void findById_WhenNotFound_ShouldThrowNotFoundException() {
        // Arrange
        Long id = 99L;

        when(personPersistencePort.findById(id)).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personUseCase.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().equals("Person not found"))
                .verify();

        verify(personPersistencePort).findById(id);
    }
}