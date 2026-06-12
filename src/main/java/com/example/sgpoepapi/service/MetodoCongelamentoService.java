package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import com.example.sgpoepapi.model.repository.MetodoCongelamentoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MetodoCongelamentoService {

    private MetodoCongelamentoRepositorio repository;

    public MetodoCongelamentoService(MetodoCongelamentoRepositorio repository) {
        this.repository = repository;
    }

    public List<MetodoCongelamento> getMetodosCongelamento() {
        return repository.findAll();
    }

    public Optional<MetodoCongelamento> getMetodoCongelamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MetodoCongelamento salvar(MetodoCongelamento metodoCongelamento) {
        validar(metodoCongelamento);
        return repository.save(metodoCongelamento);
    }

    @Transactional
    public void excluir(MetodoCongelamento metodoCongelamento) {
        Objects.requireNonNull(metodoCongelamento.getId());
        repository.delete(metodoCongelamento);
    }

    public void validar(MetodoCongelamento metodoCongelamento) {
        if (metodoCongelamento.getNomeMetodo() == null || metodoCongelamento.getNomeMetodo().trim().equals("")) {
            throw new RegraNegocioException("Nome do método inválido");
        }
        if (metodoCongelamento.getDescricao() == null || metodoCongelamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }
    }
}
