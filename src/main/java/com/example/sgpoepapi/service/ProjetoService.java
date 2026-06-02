package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Projeto;
import com.example.sgpoepapi.model.repository.ProjetoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjetoService {

    private ProjetoRepositorio repository;

    public ProjetoService(ProjetoRepositorio repository) {
        this.repository = repository;
    }

    public List<Projeto> getProjetos() {
        return repository.findAll();
    }

    public Optional<Projeto> getProjetoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Projeto salvar(Projeto projeto) {
        validar(projeto);
        return repository.save(projeto);
    }

    @Transactional
    public void excluir(Projeto projeto) {
        Objects.requireNonNull(projeto.getId());
        repository.delete(projeto);
    }

    public void validar(Projeto projeto) {
        if (projeto.getPrestadora() == null) {
            throw new RegraNegocioException("Prestadora de serviço inválida");
        }
        if (projeto.getCliente() == null) {
            throw new RegraNegocioException("Cliente inválido");
        }
        if (projeto.getDataInicioProjeto() == null) {
            throw new RegraNegocioException("Data de início do projeto inválida");
        }
        if (projeto.getDataTerminoProjeto() == null) {
            throw new RegraNegocioException("Data de término do projeto inválida");
        }
        if (projeto.getRacaEmbrioes() == null || projeto.getRacaEmbrioes().trim().equals("")) {
            throw new RegraNegocioException("Raça dos embriões inválida");
        }
    }
}
