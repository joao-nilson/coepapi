package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.QualidadeEmbriao;
import com.example.sgpoepapi.model.repository.QualidadeEmbriaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QualidadeEmbriaoService {

    private QualidadeEmbriaoRepositorio repository;

    public QualidadeEmbriaoService(QualidadeEmbriaoRepositorio repository) {
        this.repository = repository;
    }

    public List<QualidadeEmbriao> getQualidadesEmbriao() {
        return repository.findAll();
    }

    public Optional<QualidadeEmbriao> getQualidadeEmbriaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public QualidadeEmbriao salvar(QualidadeEmbriao qualidadeEmbriao) {
        validar(qualidadeEmbriao);
        return repository.save(qualidadeEmbriao);
    }

    @Transactional
    public void excluir(QualidadeEmbriao qualidadeEmbriao) {
        Objects.requireNonNull(qualidadeEmbriao.getId());
        repository.delete(qualidadeEmbriao);
    }

    public void validar(QualidadeEmbriao qualidadeEmbriao) {
        if (qualidadeEmbriao.getQualidade() == null || qualidadeEmbriao.getQualidade().trim().equals("")) {
            throw new RegraNegocioException("Qualidade inválida");
        }
    }
}
