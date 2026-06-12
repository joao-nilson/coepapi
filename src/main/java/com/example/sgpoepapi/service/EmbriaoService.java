package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.model.repository.EmbriaoRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmbriaoService {

    private EmbriaoRep repository;

    public EmbriaoService(EmbriaoRep repository) {
        this.repository = repository;
    }

    public List<Embriao> getEmbrioes() {
        return repository.findAll();
    }

    public Optional<Embriao> getEmbriaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Embriao salvar(Embriao embriao) {
        validar(embriao);
        return repository.save(embriao);
    }

    @Transactional
    public void excluir(Embriao embriao) {
        Objects.requireNonNull(embriao.getId());
        repository.delete(embriao);
    }

    public void validar(Embriao embriao) {
        if (embriao.getRaca() == null) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (embriao.getGrauDesenvolvimento() == null || embriao.getGrauDesenvolvimento().trim().equals("")) {
            throw new RegraNegocioException("Grau de desenvolvimento inválido");
        }
        if (embriao.getSexo() == null || embriao.getSexo().trim().equals("")) {
            throw new RegraNegocioException("Sexo inválido");
        }
        if (embriao.getQualidade() == null) {
            throw new RegraNegocioException("Qualidade do embrião inválida");
        }
        if (embriao.getPrecoEmbriao() == null || embriao.getPrecoEmbriao() < 0) {
            throw new RegraNegocioException("Preço do embrião inválido");
        }
    }
}
