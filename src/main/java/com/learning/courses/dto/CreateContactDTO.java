package com.learning.courses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Valid
public class CreateContactDTO implements Serializable {

    @NotBlank
    @Schema(example = "Domowy")
    private String name;

    @NotBlank
    @Schema(example = "student@example.com")
    private String email;

    @NotBlank
    @Schema(example = "ul. Akademicka 1, 00-000 Warszawa")
    private String address;

    @NotBlank
    @Schema(example = "123-456-789")
    private String phoneNumber;

    @NotNull
    private Long studentId;
}