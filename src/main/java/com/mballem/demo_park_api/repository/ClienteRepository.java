package com.mballem.demo_park_api.repository;

import com.mballem.demo_park_api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findById(Long id);
}
