package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Animal;
import com.example.sgpoepapi.model.repository.AnimalRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnimalService {

    private AnimalRepositorio repository;

    public AnimalService(AnimalRepositorio repository) {
        this.repository = repository;
    }

    public List<Animal> getAnimais() {
        return repository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Animal salvar(Animal animal) {
        validar(animal);
        return repository.save(animal);
    }

    @Transactional
    public void excluir(Animal animal) {
        Objects.requireNonNull(animal.getId());
        repository.delete(animal);
    }

    public void validar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (animal.getRaca() == null) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (animal.getProprietario() == null) {
            throw new RegraNegocioException("Proprietário inválido");
        }
    }
}