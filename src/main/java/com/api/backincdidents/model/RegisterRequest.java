package com.api.backincdidents.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "the firstname should not be null")
    @NotEmpty(message = "the firstname should not be empty")
    private String firstname;

    @NotNull(message = "the lastname should not be null")
    @NotEmpty(message = "the lastname should not be empty")
    private String lastname;

    @NotNull(message = "the email should not be null")
    @NotEmpty(message = "the email should not be empty")
    private String email;

    @NotNull(message = "the password should not be null")
    @NotEmpty(message = "the password should not be empty")
    private String password;


    @NotNull(message = "the role should not be null")
    @NotEmpty(message = "the role should not be empty")
    @Pattern(regexp = "Declarant|Assigned", message = "the role should be either DECLARANT or ASSEGNE")
    private String role;
}
