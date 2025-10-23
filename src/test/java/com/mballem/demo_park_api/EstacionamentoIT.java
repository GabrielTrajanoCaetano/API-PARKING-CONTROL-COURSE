package com.mballem.demo_park_api;

import com.mballem.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.mballem.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation(){
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                .cor("AZUL").clienteCpf("79074426050")
                .build();
        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("placa").isEqualTo("WER-1111")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO 1.0")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("clienteCpf").isEqualTo("79074426050")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();
    }

    @Test
    public void criarCheckin_ComRoleCliente_RetornarErrorStatus403(){
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("IWN-4459").marca("FIAT").modelo("DOBLO 1.4")
                .cor("BRANCA").clienteCpf("55352517047")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void criarCheckIn_ComDadosInvalidos_RetornarErrorMessage422(){
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("").marca("").modelo(" ")
                .cor("").clienteCpf("")
                .build();
        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void criarCheckIn_ComCpfInexistente_RetornarErrorMessageStatus404(){
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("IWN-5487").marca("FORD").modelo("FIESTA 1.0")
                .cor("BRANCO").clienteCpf("70142989096")
                .build();
        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Sql(scripts = "/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void criarCheckin_ComVagasOcupadas_RetornarErrorMessage404(){
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WFA-2358").marca("VOLKSWAGEN").modelo("VOYAGE 1.6")
                .cor("AZUL").clienteCpf("55352517047")
                .build();

        testClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void buscarCheckin_ComPerfilCliente_RetornarDadosComStatus200(){
        //numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga
        testClient
                .get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20251022-101500" )
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("55352517047")
                .jsonPath("recibo").isEqualTo("20251022-101500")
                .jsonPath("dataEntrada").isEqualTo("2025-10-22 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-56");
    }
    @Test
    public void buscarCheckin_ComPerfilAdmin_RetornarDadosComStatus200(){
        //numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga
        testClient
                .get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20251022-101500" )
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("55352517047")
                .jsonPath("recibo").isEqualTo("20251022-101500")
                .jsonPath("dataEntrada").isEqualTo("2025-10-22 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-56");
    }

    @Test
    public void buscarCheckin_ComReciboInexistente_RetornarErrorMessage404(){
        testClient
                .get()
                .uri("/api/v1/estacionamentos/check-in/20240315-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"bob@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20240315-101300")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void criarCheckout_ComDadosValido_RetornarEstacionamentoComStatus200(){

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/20251022-101500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("55352517047")
                .jsonPath("recibo").isEqualTo("20251022-101500")
                .jsonPath("dataEntrada").isEqualTo("2025-10-22 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-56")
                .jsonPath("dataSaida").exists()
                .jsonPath("valor").exists()
                .jsonPath("desconto").exists();
    }

    @Test
    public void criarCheckout_ComReciboInexistentes_RetornarErrorMessageStatus404(){
        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101355")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101355")
                .jsonPath("method").isEqualTo("PUT");

    }

    @Test
    public void criarCheckout_ComAcessoClienteNegado_RetornarErrorMessageComStatus403(){
        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20251022-101500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20251022-101500")
                .jsonPath("method").isEqualTo("PUT");
    }
}

