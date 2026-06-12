package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Aspiracao;
import com.example.sgpoepapi.model.repository.AspiracaoRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AspiracaoService {

    private AspiracaoRep repository;

    public AspiracaoService(AspiracaoRep repository) {
        this.repository = repository;
    }

    public List<Aspiracao> getAspiracoes() {
        return repository.findAll();
    }

    public Optional<Aspiracao> getAspiracaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Aspiracao salvar(Aspiracao aspiracao) {
        validar(aspiracao);
        return repository.save(aspiracao);
    }

    @Transactional
    public void excluir(Aspiracao aspiracao) {
        Objects.requireNonNull(aspiracao.getId());
        repository.delete(aspiracao);
    }

    public void validar(Aspiracao aspiracao) {
        if (aspiracao.getProjeto() == null) {
            throw new RegraNegocioException("Projeto inválido");
        }
        if (aspiracao.getDataEtapa() == null) {
            throw new RegraNegocioException("Data da etapa inválida");
        }
        if (aspiracao.getHoraInicio() == null) {
            throw new RegraNegocioException("Hora de início inválida");
        }
        if (aspiracao.getHoraTermino() == null) {
            throw new RegraNegocioException("Hora de término inválida");
        }
        if (aspiracao.getDoadoras() == null || aspiracao.getDoadoras().isEmpty()) {
            throw new RegraNegocioException("Lista de doadoras inválida");
        }
        if (aspiracao.getTempChupada() == null || aspiracao.getTempChupada() < 0) {
            throw new RegraNegocioException("Temperatura de chupada inválida");
        }
    }
}
