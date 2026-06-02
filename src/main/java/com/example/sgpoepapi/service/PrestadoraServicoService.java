package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.PrestadoraServico;
import com.example.sgpoepapi.model.repository.PrestadoraServicoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrestadoraServicoService {

    private PrestadoraServicoRepositorio repository;

    public PrestadoraServicoService(PrestadoraServicoRepositorio repository) {
        this.repository = repository;
    }

    public List<PrestadoraServico> getPrestadorasServico() {
        return repository.findAll();
    }

    public Optional<PrestadoraServico> getPrestadoraServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public PrestadoraServico salvar(PrestadoraServico prestadoraServico) {
        validar(prestadoraServico);
        return repository.save(prestadoraServico);
    }

    @Transactional
    public void excluir(PrestadoraServico prestadoraServico) {
        Objects.requireNonNull(prestadoraServico.getId());
        repository.delete(prestadoraServico);
    }

    public void validar(PrestadoraServico prestadoraServico) {
        if (prestadoraServico.getNome() == null || prestadoraServico.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (prestadoraServico.getCNPJ() == null || prestadoraServico.getCNPJ().trim().equals("")) {
            throw new RegraNegocioException("CNPJ inválido");
        }
        if (prestadoraServico.getEndereco() == null || prestadoraServico.getEndereco().trim().equals("")) {
            throw new RegraNegocioException("Endereço inválido");
        }
        if (prestadoraServico.getNum_registro_mapa() == null || prestadoraServico.getNum_registro_mapa().trim().equals("")) {
            throw new RegraNegocioException("Número de registro MAPA inválido");
        }
        if (prestadoraServico.getTelefone() == null || prestadoraServico.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (prestadoraServico.getEmail() == null || prestadoraServico.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }
}
