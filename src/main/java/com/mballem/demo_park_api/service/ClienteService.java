package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.Cliente;
import com.mballem.demo_park_api.exception.CpfUniqueViolationException;
import com.mballem.demo_park_api.exception.EntityNotFoundException;
import com.mballem.demo_park_api.repository.ClienteRepository;
import com.mballem.demo_park_api.repository.projection.ClienteProjection;
import com.mballem.demo_park_api.web.dto.ClienteResponseDto;
import com.mballem.demo_park_api.web.dto.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        try{
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(String.format("CPF %s nao pode ser cadastrado, ja existe no sistema", cliente.getCpf()));
        }

    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Cliente id= %s não pode ser encontrado", id)));
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> findAll(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException(String.format("Cliente com CPF %s não encontrado", cpf)));
    }
}
