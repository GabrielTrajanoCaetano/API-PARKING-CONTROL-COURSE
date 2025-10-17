package com.mballem.demo_park_api.web.controller;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.service.EstacionamentoService;
import com.mballem.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import com.mballem.demo_park_api.web.dto.mapper.ClienteVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto dto){
        ClienteVagas clienteVagas = ClienteVagaMapper.toClienteVaga(dto);
        estacionamentoService.checkIn(clienteVagas);
        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVagas);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVagas.getRecibo())
                .toUri();

                return ResponseEntity.created(location).body(responseDto);
    }
}
