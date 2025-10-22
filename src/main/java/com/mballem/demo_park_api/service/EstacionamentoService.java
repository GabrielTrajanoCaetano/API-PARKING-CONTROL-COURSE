package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.Cliente;
import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.entity.Vaga;
import com.mballem.demo_park_api.exception.EntityNotFoundException;
import com.mballem.demo_park_api.repository.ClienteVagaRepository;
import com.mballem.demo_park_api.util.EstacionamentoUtils;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;
    private final ClienteVagaRepository vagaRepository;

    @Transactional
    public ClienteVagas checkIn(ClienteVagas clienteVagas){
        Cliente cliente = clienteService.buscarPorCpf(clienteVagas.getCliente().getCpf());
        clienteVagas.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVagas.setVaga(vaga);

        clienteVagas.setDataEntrada(LocalDateTime.now());

        clienteVagas.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVagas);
    }


}
