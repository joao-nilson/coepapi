package com.example.sgpoepapi.model.repository;

import com.example.sgpoepapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRep extends JpaRepository<Cliente, Long> {
}
