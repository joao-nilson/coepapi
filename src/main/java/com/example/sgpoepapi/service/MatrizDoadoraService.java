package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MatrizDoadora;
import com.example.sgpoepapi.model.repository.MatrizDoadoraRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatrizDoadoraService {

    private MatrizDoadoraRepositorio repository;

    public MatrizDoadoraService(MatrizDoadoraRepositorio repository) {
        this.repository = repository;
    }

    public List<MatrizDoadora> getMatrizesDoadadoras() {
        return repository.findAll();
    }

    public Optional<MatrizDoadora> getMatrizDoadoraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MatrizDoadora salvar(MatrizDoadora matrizDoadora) {
        validar(matrizDoadora);
        return repository.save(matrizDoadora);
    }

    @Transactional
    public void excluir(MatrizDoadora matrizDoadora) {
        Objects.requireNonNull(matrizDoadora.getId());
        repository.delete(matrizDoadora);
    }

    public void validar(MatrizDoadora matrizDoadora) {
        if (matrizDoadora.getNome() == null || matrizDoadora.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (matrizDoadora.getRaca() == null) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (matrizDoadora.getProprietario() == null) {
            throw new RegraNegocioException("Proprietário inválido");
        }
        if (matrizDoadora.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de nascimento inválida");
        }
        if (matrizDoadora.getViavel() == null) {
            throw new RegraNegocioException("Viabilidade inválida");
        }
        if (matrizDoadora.getPrecoOocito() == null || matrizDoadora.getPrecoOocito() < 0) {
            throw new RegraNegocioException("Preço do oócito inválido");
        }
    }
}
