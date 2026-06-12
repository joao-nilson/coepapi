package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Meio;
import com.example.sgpoepapi.model.repository.MeioRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MeioService {

    private MeioRepositorio repository;

    public MeioService(MeioRepositorio repository) {
        this.repository = repository;
    }

    public List<Meio> getMeios() {
        return repository.findAll();
    }

    public Optional<Meio> getMeioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Meio salvar(Meio meio) {
        validar(meio);
        return repository.save(meio);
    }

    @Transactional
    public void excluir(Meio meio) {
        Objects.requireNonNull(meio.getId());
        repository.delete(meio);
    }

    public void validar(Meio meio) {
        if (meio.getTipoDeMeio() == null || meio.getTipoDeMeio().trim().equals("")) {
            throw new RegraNegocioException("Tipo de meio inválido");
        }
        if (meio.getDescricao() == null || meio.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }
        if (meio.getPrecoDeMeio() == null || meio.getPrecoDeMeio() < 0) {
            throw new RegraNegocioException("Preço do meio inválido");
        }
        if (meio.getQuantidade() == null || meio.getQuantidade() <= 0) {
            throw new RegraNegocioException("Quantidade inválida");
        }
    }
}
