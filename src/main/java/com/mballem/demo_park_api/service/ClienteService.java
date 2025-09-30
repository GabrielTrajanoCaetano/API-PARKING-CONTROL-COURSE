package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.Cliente;
import com.mballem.demo_park_api.exception.CpfUniqueViolationException;
import com.mballem.demo_park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        try{
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(String.format("CPF 's%' nao pode ser cadastrado, ja existe no sistema", cliente.getCpf()));
        }
    }
}
