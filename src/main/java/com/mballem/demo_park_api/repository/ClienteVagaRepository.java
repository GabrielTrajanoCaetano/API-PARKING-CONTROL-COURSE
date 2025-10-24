package com.mballem.demo_park_api.repository;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.repository.projection.ClienteVagaProjection;
import com.mballem.demo_park_api.service.ClienteVagaService;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ClienteVagaRepository extends JpaRepository<ClienteVagas, Long> {

    Optional<ClienteVagas> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

    Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);
}
