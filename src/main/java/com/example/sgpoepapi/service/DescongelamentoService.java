package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Descongelamento;
import com.example.sgpoepapi.model.repository.DescongelamentoRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DescongelamentoService {

    private DescongelamentoRep repository;

    public DescongelamentoService(DescongelamentoRep repository) {
        this.repository = repository;
    }

    public List<Descongelamento> getDescongelamentos() {
        return repository.findAll();
    }

    public Optional<Descongelamento> getDescongelamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Descongelamento salvar(Descongelamento descongelamento) {
        validar(descongelamento);
        return repository.save(descongelamento);
    }

    @Transactional
    public void excluir(Descongelamento descongelamento) {
        Objects.requireNonNull(descongelamento.getId());
        repository.delete(descongelamento);
    }

    public void validar(Descongelamento descongelamento) {
        if (descongelamento.getEmbriao() == null) {
            throw new RegraNegocioException("Embrião inválido");
        }
        if (descongelamento.getMetodo() == null) {
            throw new RegraNegocioException("Método de descongelamento inválido");
        }
        if (descongelamento.getDataDescong() == null) {
            throw new RegraNegocioException("Data de descongelamento inválida");
        }
        if (descongelamento.getPrecoDescongelamento() == null || descongelamento.getPrecoDescongelamento() < 0) {
            throw new RegraNegocioException("Preço do descongelamento inválido");
        }
    }
}
