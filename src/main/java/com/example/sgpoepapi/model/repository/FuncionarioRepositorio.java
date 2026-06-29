package com.example.sgpoepapi.model.repository;

import com.example.sgpoepapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByLogin(String login);
}
