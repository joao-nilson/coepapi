package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Funcionario;
import com.example.sgpoepapi.model.repository.FuncionarioRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepositorio repository;

    public FuncionarioService(FuncionarioRepositorio repository) {
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().equals("")) {
            throw new RegraNegocioException("Cargo inválido");
        }
    }
}
