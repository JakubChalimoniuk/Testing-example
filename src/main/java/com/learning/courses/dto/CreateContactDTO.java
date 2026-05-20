package com.learning.courses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Schema(example = "Home / Work")
    private String name;

    @Email(message = "Invalid email format")
    @Schema(example = "student@example.com")
    private String email;

    @Schema(example = "ul. Akademicka 1, 00-000 Warszawa")
    private String address;

    @Pattern(regexp = "^\\+?[0-9\\-\\s]*$", message = "Invalid phone number format")
    @Schema(example = "123-456-789")
    private String phoneNumber;

    @NotNull
    private Long studentId;
}