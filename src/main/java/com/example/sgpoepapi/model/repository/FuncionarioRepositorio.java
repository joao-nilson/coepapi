package com.example.sgpoepapi.model.repository;

import com.example.sgpoepapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
}
