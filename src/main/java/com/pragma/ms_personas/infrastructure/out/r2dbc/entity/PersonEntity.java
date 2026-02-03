package com.pragma.ms_personas.infrastructure.out.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    private Long id;
    private String name;
    private String identificationNumber;
    private String email;
}
