package com.api.backincdidents.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPassword {
    
    private String email;
    private String token;
    private String password;
}
