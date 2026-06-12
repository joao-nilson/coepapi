package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.OocitoClassificacao;
import com.example.sgpoepapi.model.repository.OocitoClassificacaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OocitoClassificacaoService {

    private OocitoClassificacaoRepositorio repository;

    public OocitoClassificacaoService(OocitoClassificacaoRepositorio repository) {
        this.repository = repository;
    }

    public List<OocitoClassificacao> getOocitosClassificacao() {
        return repository.findAll();
    }

    public Optional<OocitoClassificacao> getOocitoClassificacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public OocitoClassificacao salvar(OocitoClassificacao oocitoClassificacao) {
        validar(oocitoClassificacao);
        return repository.save(oocitoClassificacao);
    }

    @Transactional
    public void excluir(OocitoClassificacao oocitoClassificacao) {
        Objects.requireNonNull(oocitoClassificacao.getId());
        repository.delete(oocitoClassificacao);
    }

    public void validar(OocitoClassificacao oocitoClassificacao) {
        if (oocitoClassificacao.getClassificacao() == null || oocitoClassificacao.getClassificacao().trim().equals("")) {
            throw new RegraNegocioException("Classificação inválida");
        }
        if (oocitoClassificacao.getDescricaoClass() == null || oocitoClassificacao.getDescricaoClass().trim().equals("")) {
            throw new RegraNegocioException("Descrição da classificação inválida");
        }
    }
}
