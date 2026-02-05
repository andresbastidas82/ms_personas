package com.pragma.ms_personas.infrastructure.input.rest.router;

import com.pragma.ms_personas.application.dto.PersonRequest;
import com.pragma.ms_personas.infrastructure.input.rest.handler.PersonHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/person",
                    method = RequestMethod.POST,
                    beanClass = PersonHandler.class,
                    beanMethod = "createPerson",
                    operation = @Operation(
                            operationId = "createPerson",
                            summary = "Registrar una persona",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = PersonRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Persona creada")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/person/{id}",
                    method = RequestMethod.GET,
                    beanClass = PersonHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findById",
                            summary = "Buscar una persona por su id",
                            parameters = {
                                    @Parameter(name = "id", description = "Id de la persona", required = true, in = ParameterIn.PATH )
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Datos de la persona")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> personRoutes(PersonHandler personHandler) {
        return route(POST("/api/v1/person"), personHandler::createPerson)
                .andRoute(GET("/api/v1/person/{id}"), personHandler::findById);
    }
}
