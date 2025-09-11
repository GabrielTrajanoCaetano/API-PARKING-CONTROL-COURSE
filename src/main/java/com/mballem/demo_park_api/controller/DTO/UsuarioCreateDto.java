package com.mballem.demo_park_api.controller.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString

public class UsuarioCreateDto {

    private String username;
    private String password;
}
