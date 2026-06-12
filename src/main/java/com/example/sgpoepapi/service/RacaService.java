package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.model.repository.RacaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RacaService {

    private RacaRepositorio repository;

    public RacaService(RacaRepositorio repository) {
        this.repository = repository;
    }

    public List<Raca> getRacas() {
        return repository.findAll();
    }

    public Optional<Raca> getRacaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Raca salvar(Raca raca) {
        validar(raca);
        return repository.save(raca);
    }

    @Transactional
    public void excluir(Raca raca) {
        Objects.requireNonNull(raca.getId());
        repository.delete(raca);
    }

    public void validar(Raca raca) {
        if (raca.getRaca() == null || raca.getRaca().trim().equals("")) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (raca.getFracao() == null) {
            throw new RegraNegocioException("Fração inválida");
        }
        if (raca.getFracao() < 0 || raca.getFracao() > 1) {
            throw new RegraNegocioException("Fração deve estar entre 0 e 1");
        }
    }
}
