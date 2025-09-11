package com.mballem.demo_park_api.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioResponseDto {

    private Long id;
    private String username;
    private String role;
}
