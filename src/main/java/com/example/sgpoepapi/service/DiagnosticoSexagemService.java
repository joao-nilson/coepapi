package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DiagnosticoSexagem;
import com.example.sgpoepapi.model.repository.DiagnosticoSexagemRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiagnosticoSexagemService {

    private DiagnosticoSexagemRep repository;

    public DiagnosticoSexagemService(DiagnosticoSexagemRep repository) {
        this.repository = repository;
    }

    public List<DiagnosticoSexagem> getDiagnosticosSexagem() {
        return repository.findAll();
    }

    public Optional<DiagnosticoSexagem> getDiagnosticoSexagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public DiagnosticoSexagem salvar(DiagnosticoSexagem diagnosticoSexagem) {
        validar(diagnosticoSexagem);
        return repository.save(diagnosticoSexagem);
    }

    @Transactional
    public void excluir(DiagnosticoSexagem diagnosticoSexagem) {
        Objects.requireNonNull(diagnosticoSexagem.getId());
        repository.delete(diagnosticoSexagem);
    }

    public void validar(DiagnosticoSexagem diagnosticoSexagem) {
        if (diagnosticoSexagem.getMachos() == null) {
            throw new RegraNegocioException("Lista de machos inválida");
        }
        if (diagnosticoSexagem.getFemeas() == null) {
            throw new RegraNegocioException("Lista de fêmeas inválida");
        }
    }
}
