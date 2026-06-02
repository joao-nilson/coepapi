package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Oocito;
import com.example.sgpoepapi.model.repository.OocitoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OocitoService {

    private OocitoRepositorio repository;

    public OocitoService(OocitoRepositorio repository) {
        this.repository = repository;
    }

    public List<Oocito> getOocitos() {
        return repository.findAll();
    }

    public Optional<Oocito> getOocitoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Oocito salvar(Oocito oocito) {
        validar(oocito);
        return repository.save(oocito);
    }

    @Transactional
    public void excluir(Oocito oocito) {
        Objects.requireNonNull(oocito.getId());
        repository.delete(oocito);
    }

    public void validar(Oocito oocito) {
        if (oocito.getMae() == null) {
            throw new RegraNegocioException("Matriz doadora (mãe) inválida");
        }
        if (oocito.getViavel() == null) {
            throw new RegraNegocioException("Viabilidade inválida");
        }
        if (oocito.getClassificacao() == null) {
            throw new RegraNegocioException("Classificação inválida");
        }
        if (oocito.getPrecoOocito() == null || oocito.getPrecoOocito() < 0) {
            throw new RegraNegocioException("Preço do oócito inválido");
        }
    }
}
