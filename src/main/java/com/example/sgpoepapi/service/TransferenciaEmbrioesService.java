package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.TransferenciaEmbrioes;
import com.example.sgpoepapi.model.repository.TransferenciaEmbrioesRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferenciaEmbrioesService {

    private TransferenciaEmbrioesRepositorio repository;

    public TransferenciaEmbrioesService(TransferenciaEmbrioesRepositorio repository) {
        this.repository = repository;
    }

    public List<TransferenciaEmbrioes> getTransferenciasEmbrioes() {
        return repository.findAll();
    }

    public Optional<TransferenciaEmbrioes> getTransferenciaEmbrioesById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TransferenciaEmbrioes salvar(TransferenciaEmbrioes transferenciaEmbrioes) {
        validar(transferenciaEmbrioes);
        return repository.save(transferenciaEmbrioes);
    }

    @Transactional
    public void excluir(TransferenciaEmbrioes transferenciaEmbrioes) {
        Objects.requireNonNull(transferenciaEmbrioes.getId());
        repository.delete(transferenciaEmbrioes);
    }

    public void validar(TransferenciaEmbrioes transferenciaEmbrioes) {
        if (transferenciaEmbrioes.getProjeto() == null) {
            throw new RegraNegocioException("Projeto inválido");
        }
        if (transferenciaEmbrioes.getDataEtapa() == null) {
            throw new RegraNegocioException("Data da etapa inválida");
        }
        if (transferenciaEmbrioes.getEmbrioes() == null || transferenciaEmbrioes.getEmbrioes().isEmpty()) {
            throw new RegraNegocioException("Lista de embriões inválida");
        }
        if (transferenciaEmbrioes.getHoraEntregaEmb() == null) {
            throw new RegraNegocioException("Hora de entrega dos embriões inválida");
        }
    }
}
