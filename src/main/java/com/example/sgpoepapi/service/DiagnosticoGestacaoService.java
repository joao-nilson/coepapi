package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DiagnosticoGestacao;
import com.example.sgpoepapi.model.repository.DiagnosticoGestacaoRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiagnosticoGestacaoService {

    private DiagnosticoGestacaoRep repository;

    public DiagnosticoGestacaoService(DiagnosticoGestacaoRep repository) {
        this.repository = repository;
    }

    public List<DiagnosticoGestacao> getDiagnosticosGestacao() {
        return repository.findAll();
    }

    public Optional<DiagnosticoGestacao> getDiagnosticoGestacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public DiagnosticoGestacao salvar(DiagnosticoGestacao diagnosticoGestacao) {
        validar(diagnosticoGestacao);
        return repository.save(diagnosticoGestacao);
    }

    @Transactional
    public void excluir(DiagnosticoGestacao diagnosticoGestacao) {
        Objects.requireNonNull(diagnosticoGestacao.getId());
        repository.delete(diagnosticoGestacao);
    }

    public void validar(DiagnosticoGestacao diagnosticoGestacao) {
        if (diagnosticoGestacao.getPrenhas() == null) {
            throw new RegraNegocioException("Lista de prenhas inválida");
        }
        if (diagnosticoGestacao.getVazias() == null) {
            throw new RegraNegocioException("Lista de vazias inválida");
        }
    }
}
