package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Congelamento;
import com.example.sgpoepapi.model.repository.CongelamentoRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CongelamentoService {

    private CongelamentoRep repository;

    public CongelamentoService(CongelamentoRep repository) {
        this.repository = repository;
    }

    public List<Congelamento> getCongelamentos() {
        return repository.findAll();
    }

    public Optional<Congelamento> getCongelamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Congelamento salvar(Congelamento congelamento) {
        validar(congelamento);
        return repository.save(congelamento);
    }

    @Transactional
    public void excluir(Congelamento congelamento) {
        Objects.requireNonNull(congelamento.getId());
        repository.delete(congelamento);
    }

    public void validar(Congelamento congelamento) {
        if (congelamento.getEmbriao() == null) {
            throw new RegraNegocioException("Embrião inválido");
        }
        if (congelamento.getMetodo() == null) {
            throw new RegraNegocioException("Método de congelamento inválido");
        }
        if (congelamento.getDataCong() == null) {
            throw new RegraNegocioException("Data de congelamento inválida");
        }
        if (congelamento.getPrecoCongelamento() == null || congelamento.getPrecoCongelamento() < 0) {
            throw new RegraNegocioException("Preço do congelamento inválido");
        }
    }
}
