package com.mballem.demo_park_api.web.controller;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.service.ClienteVagaService;
import com.mballem.demo_park_api.service.EstacionamentoService;
import com.mballem.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import com.mballem.demo_park_api.web.dto.mapper.ClienteVagaMapper;
import com.mballem.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@Tag(name = "Estacionamento", description = "Contém todas as operações referente ao recurso de estacionamento")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final ClienteVagaService clienteVagaService;

    @Operation(summary = "Operação de check-in", description = "Recurso para dar entrada de um veículo no estacionamento. " +
    "Requisição exige uso de um bearer token. Acesso restrito a Role = 'ADMIN'",
    security = @SecurityRequirement(name = "security"),
    responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso ao recurso criado"),
                    content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = EstacionamentoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Causas possiveis <br/>" +
            "- CPF do cliente não cadastrado no sistema; <br/>" +
            "- Nenhuma vaga livre foi localizada;",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou inválidos",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil",
            content = @Content(mediaType = "application/json;charset=UTF8", schema = @Schema(implementation = ErrorMessage.class)))
    })

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

    @Operation(summary = "Localizar um veículo no estacionamento", description = "Recurso para retornar um veiculo estacionado " +
            "Pelo nº do recibo. Requisição exige uso de um bearer token",
    security = @SecurityRequirement(name = "security"),
    parameters = {
            @Parameter(in = PATH, name = "recibo", description = "Numero de recibo gerado pelo check-in")
    },
    responses = {
            @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
            content = @Content(mediaType = "application/json;charset= UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Numero de recibo não encontrado",
            content = @Content(mediaType = "application/json;charset= UTF-8", schema = @Schema(implementation = ErrorMessage.class)))

    })

    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo){
            ClienteVagas vaga = clienteVagaService.buscarVagaPeloRecibo(recibo);
            EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(vaga);
            return ResponseEntity.ok(dto);
    }
    @Operation(summary = "Operação de check-out", description = "Recurso para dar saida de um veiculo do estacionamento. " +
    "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
    security = @SecurityRequirement(name = "security"),
    parameters = {
            @Parameter(in = PATH, name ="recibo", description = "Número do recibo gerado pelo check-in")
    },
    responses = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Numero de recibo inexistente ou " +
                    "o veículo já passou pelo check-out.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    })

    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkOut(@PathVariable String recibo){
        ClienteVagas vaga = estacionamentoService.checkOut(recibo);
        EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(vaga);
        return ResponseEntity.ok(dto);
    }


}
