package com.mballem.demo_park_api.repository;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ClienteVagaRepository extends JpaRepository<ClienteVagas, Long> {

    Optional<ClienteVagas> findByReciboAndDataSaidaIsNull(String recibo);
}
