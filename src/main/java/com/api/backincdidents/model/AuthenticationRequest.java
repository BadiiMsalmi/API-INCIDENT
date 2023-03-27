package com.api.backincdidents.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "email should not be null")
    @NotEmpty(message = "email should not be empty")
    private String email;

    @NotNull(message = "password should not be null")
    @NotEmpty(message = "password should not be empty")
    String password;
}
