package com.mballem.demo_park_api.repository;

import com.mballem.demo_park_api.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
}
