package com.pragma.ms_personas.infrastructure.out.r2dbc.adapter;

import com.pragma.ms_personas.domain.model.Person;
import com.pragma.ms_personas.infrastructure.out.r2dbc.entity.PersonEntity;
import com.pragma.ms_personas.infrastructure.out.r2dbc.mapper.IPersonEntityMapper;
import com.pragma.ms_personas.infrastructure.out.r2dbc.repository.IPersonR2dbcRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryAdapterTest {

    @Mock
    private IPersonR2dbcRepository personR2dbcRepository;

    @Mock
    private IPersonEntityMapper personEntityMapper;

    @InjectMocks
    private PersonRepositoryAdapter personRepositoryAdapter;

    // --- TEST: save ---

    @Test
    @DisplayName("Save: Should map to entity, save in repo, and map back to model")
    void save_ShouldPersistAndReturnModel() {
        // Arrange
        Person personModel = new Person(null, "Juan", "123456", "juan@test.com");

        // Simulamos la entidad (ajusta los setters según tu clase real)
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("Juan");

        PersonEntity savedEntity = new PersonEntity();
        savedEntity.setId(1L);
        savedEntity.setName("Juan");

        Person savedModel = new Person(1L, "Juan", "123456", "juan@test.com");

        // 1. Mapeo de Dominio -> Entidad
        when(personEntityMapper.toEntity(personModel)).thenReturn(personEntity);

        // 2. Guardado en Repositorio
        when(personR2dbcRepository.save(personEntity)).thenReturn(Mono.just(savedEntity));

        // 3. Mapeo de Entidad -> Dominio
        when(personEntityMapper.toModel(savedEntity)).thenReturn(savedModel);

        // Act
        Mono<Person> result = personRepositoryAdapter.save(personModel);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedModel)
                .verifyComplete();

        // Verificaciones de interacción
        verify(personEntityMapper).toEntity(personModel);
        verify(personR2dbcRepository).save(personEntity);
        verify(personEntityMapper).toModel(savedEntity);
    }

    // --- TEST: findById ---

    @Test
    @DisplayName("FindById: Should return model when found")
    void findById_WhenFound_ShouldReturnModel() {
        // Arrange
        Long id = 1L;
        PersonEntity foundEntity = new PersonEntity();
        foundEntity.setId(id);

        Person foundModel = new Person(id, "Juan", "123", "juan@test.com");

        when(personR2dbcRepository.findById(id)).thenReturn(Mono.just(foundEntity));
        when(personEntityMapper.toModel(foundEntity)).thenReturn(foundModel);

        // Act
        Mono<Person> result = personRepositoryAdapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(foundModel)
                .verifyComplete();

        verify(personR2dbcRepository).findById(id);
        verify(personEntityMapper).toModel(foundEntity);
    }

    @Test
    @DisplayName("FindById: Should return empty Mono when not found")
    void findById_WhenNotFound_ShouldReturnEmpty() {
        // Arrange
        Long id = 99L;
        when(personR2dbcRepository.findById(id)).thenReturn(Mono.empty());

        // Act
        Mono<Person> result = personRepositoryAdapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .verifyComplete(); // Esperamos que termine sin emitir nada

        verify(personR2dbcRepository).findById(id);
        // Verificamos que NO se llame al mapper si no hay resultados
        verifyNoInteractions(personEntityMapper);
    }
}