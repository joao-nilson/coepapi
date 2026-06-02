package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.FIV;
import com.example.sgpoepapi.model.repository.FIVRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FIVService {

    private FIVRepositorio repository;

    public FIVService(FIVRepositorio repository) {
        this.repository = repository;
    }

    public List<FIV> getFIVs() {
        return repository.findAll();
    }

    public Optional<FIV> getFIVById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FIV salvar(FIV fiv) {
        validar(fiv);
        return repository.save(fiv);
    }

    @Transactional
    public void excluir(FIV fiv) {
        Objects.requireNonNull(fiv.getId());
        repository.delete(fiv);
    }

    public void validar(FIV fiv) {
        if (fiv.getMae() == null) {
            throw new RegraNegocioException("Matriz doadora (mãe) inválida");
        }
        if (fiv.getPai() == null) {
            throw new RegraNegocioException("Macho reprodutor (pai) inválido");
        }
        if (fiv.getDataFIV() == null) {
            throw new RegraNegocioException("Data da FIV inválida");
        }
        if (fiv.getNOocitosFecundados() == null || fiv.getNOocitosFecundados() < 0) {
            throw new RegraNegocioException("Número de oócitos fecundados inválido");
        }
        if (fiv.getNEmbrioes() == null || fiv.getNEmbrioes() < 0) {
            throw new RegraNegocioException("Número de embriões inválido");
        }
    }
}
