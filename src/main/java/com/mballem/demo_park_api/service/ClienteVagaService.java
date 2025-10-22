package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.exception.EntityNotFoundException;
import com.mballem.demo_park_api.repository.ClienteVagaRepository;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {

    private final ClienteVagaRepository repository;

    @Transactional
    public ClienteVagas salvar(ClienteVagas clienteVagas){
        return repository.save(clienteVagas);
    }

    @Transactional(readOnly = true)
    public ClienteVagas buscarVagaPeloRecibo(String recibo) {
       return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(() -> new EntityNotFoundException(String.format("Recibo %s não encontrado no sistema ou check-out já realizado", recibo)));
    }
}
