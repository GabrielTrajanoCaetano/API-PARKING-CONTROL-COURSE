package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
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
}
