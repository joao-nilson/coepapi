package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Sincronizacao;
import com.example.sgpoepapi.model.repository.SincronizacaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SincronizacaoService {

    private SincronizacaoRepositorio repository;

    public SincronizacaoService(SincronizacaoRepositorio repository) {
        this.repository = repository;
    }

    public List<Sincronizacao> getSincronizacoes() {
        return repository.findAll();
    }

    public Optional<Sincronizacao> getSincronizacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Sincronizacao salvar(Sincronizacao sincronizacao) {
        validar(sincronizacao);
        return repository.save(sincronizacao);
    }

    @Transactional
    public void excluir(Sincronizacao sincronizacao) {
        Objects.requireNonNull(sincronizacao.getId());
        repository.delete(sincronizacao);
    }

    public void validar(Sincronizacao sincronizacao) {
        if (sincronizacao.getProjeto() == null) {
            throw new RegraNegocioException("Projeto inválido");
        }
        if (sincronizacao.getDataEtapa() == null) {
            throw new RegraNegocioException("Data da etapa inválida");
        }
        if (sincronizacao.getReceptoras() == null || sincronizacao.getReceptoras().isEmpty()) {
            throw new RegraNegocioException("Lista de receptoras inválida");
        }
    }
}
