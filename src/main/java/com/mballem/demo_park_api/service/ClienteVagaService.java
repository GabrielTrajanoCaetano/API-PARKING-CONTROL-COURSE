package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.exception.EntityNotFoundException;
import com.mballem.demo_park_api.repository.ClienteVagaRepository;
import com.mballem.demo_park_api.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
       return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(() -> new EntityNotFoundException("Recibo", recibo));
    }

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }
    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClienteCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorUsuario(Long id, Pageable pageable) {
        return repository.findAllByClienteUsuarioId(id, pageable);
    }
}
