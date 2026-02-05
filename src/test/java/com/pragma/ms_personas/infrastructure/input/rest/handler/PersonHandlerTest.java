package com.pragma.ms_personas.infrastructure.input.rest.handler;

import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.application.dto.PersonResponse;
import com.pragma.ms_personas.application.helper.IPersonHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonHandlerTest {

    @Mock
    private IPersonHelper personHelper;

    @InjectMocks
    private PersonHandler personHandler;

    // --- TEST: createPerson ---

    @Test
    @DisplayName("Create Person: Should return 200 OK when successful")
    void createPerson_ShouldReturnOk() {
        // Arrange
        PersonRequest requestDto = new PersonRequest();

        // Asumimos que el helper retorna un PersonResponse
        PersonResponse responseDto = new PersonResponse();

        MockServerRequest request = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(personHelper.createPerson(any(PersonRequest.class)))
                .thenReturn(Mono.just(responseDto));

        // Act
        Mono<ServerResponse> result = personHandler.createPerson(request);

        // Assert
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                })
                .verifyComplete();

        verify(personHelper).createPerson(any(PersonRequest.class));
    }

    // --- TEST: findById ---

    @Test
    @DisplayName("Find By Id: Should return 200 OK when found")
    void findById_ShouldReturnOk() {
        // Arrange
        Long id = 1L;
        PersonResponse responseDto = new PersonResponse();

        MockServerRequest request = MockServerRequest.builder()
                .pathVariable("id", String.valueOf(id))
                .build();

        when(personHelper.findById(id)).thenReturn(Mono.just(responseDto));

        // Act
        Mono<ServerResponse> result = personHandler.findById(request);

        // Assert
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                })
                .verifyComplete();

        verify(personHelper).findById(id);
    }
}