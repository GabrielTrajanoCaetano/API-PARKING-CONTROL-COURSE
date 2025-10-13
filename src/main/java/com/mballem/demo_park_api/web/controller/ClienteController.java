package com.mballem.demo_park_api.web.controller;

import com.mballem.demo_park_api.entity.Cliente;
import com.mballem.demo_park_api.jwt.JwtUserDetails;
import com.mballem.demo_park_api.repository.projection.ClienteProjection;
import com.mballem.demo_park_api.service.ClienteService;
import com.mballem.demo_park_api.service.UsuarioService;
import com.mballem.demo_park_api.web.dto.ClienteCreateDto;
import com.mballem.demo_park_api.web.dto.ClienteResponseDto;
import com.mballem.demo_park_api.web.dto.PageableDto;
import com.mballem.demo_park_api.web.dto.mapper.ClienteMapper;
import com.mballem.demo_park_api.web.dto.mapper.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Contém todas as operações relativas ao recurso de um cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente vinculado a um usuário cadastrado." +
            "Requisição exige uso de um bearer token. Acesso restrito a Role = 'CLIENTE'",
    responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Cliente CPF já possui cadastro no sistema",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processo por falta de dados ou dados invalidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de ADMIN",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })



    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.tocliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));

    }

    @Operation(summary = "Buscar um cliente pelo id", description = "Recurso para buscar um cliente através de um identificador" +
            "Requisição de uso liberada apenas para Administrador",
            responses = {
                @ApiResponse(responseCode = "200", description = "Recurso retorna um cliente a partir de um identificador",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),

                @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id){
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }

    @Operation(summary = "Recupera lista de clientes", description = "Requisição exige uso de um bearer token. Acesso restrito a Role= 'ADMIN'",
    security = @SecurityRequirement(name = "security"),
    parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "page",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                    description = "Representa a pagina retornada"),
            @Parameter(in = ParameterIn.QUERY, name = "size",
                    content = @Content(schema = @Schema(type = "integer",defaultValue = "20")),
                    description = "Representa o total de elementos por pagina"),
            @Parameter(in = ParameterIn.QUERY, name = "sort",
                    content = @Content(schema = @Schema(type = "string", defaultValue = "id,asc")),
                    description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados")
    },
    responses = {
            @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    })


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable){
            Page<ClienteProjection> clientes = clienteService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clientes));
    }
}