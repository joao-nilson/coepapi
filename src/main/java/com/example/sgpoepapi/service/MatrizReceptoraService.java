package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MatrizReceptora;
import com.example.sgpoepapi.model.repository.MatrizReceptoraRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatrizReceptoraService {

    private MatrizReceptoraRepositorio repository;

    public MatrizReceptoraService(MatrizReceptoraRepositorio repository) {
        this.repository = repository;
    }

    public List<MatrizReceptora> getMatrizesReceptoras() {
        return repository.findAll();
    }

    public Optional<MatrizReceptora> getMatrizReceptoraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MatrizReceptora salvar(MatrizReceptora matrizReceptora) {
        validar(matrizReceptora);
        return repository.save(matrizReceptora);
    }

    @Transactional
    public void excluir(MatrizReceptora matrizReceptora) {
        Objects.requireNonNull(matrizReceptora.getId());
        repository.delete(matrizReceptora);
    }

    public void validar(MatrizReceptora matrizReceptora) {
        if (matrizReceptora.getNome() == null || matrizReceptora.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (matrizReceptora.getRaca() == null) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (matrizReceptora.getProprietario() == null) {
            throw new RegraNegocioException("Proprietário inválido");
        }
        if (matrizReceptora.getNumero() == null) {
            throw new RegraNegocioException("Número inválido");
        }
        if (matrizReceptora.getPrenha() == null) {
            throw new RegraNegocioException("Status de prenhez inválido");
        }
        if (matrizReceptora.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de nascimento inválida");
        }
    }
}
