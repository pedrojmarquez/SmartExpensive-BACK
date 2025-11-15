package com.smartexpensive.backend.security.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {

    private String nombre;
    private String email;
    private String password;
    
}
