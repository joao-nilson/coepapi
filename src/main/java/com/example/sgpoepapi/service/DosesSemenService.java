package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DosesSemen;
import com.example.sgpoepapi.model.repository.DosesSemenRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DosesSemenService {

    private DosesSemenRep repository;

    public DosesSemenService(DosesSemenRep repository) {
        this.repository = repository;
    }

    public List<DosesSemen> getDosesSemen() {
        return repository.findAll();
    }

    public Optional<DosesSemen> getDosesSemenById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public DosesSemen salvar(DosesSemen dosesSemen) {
        validar(dosesSemen);
        return repository.save(dosesSemen);
    }

    @Transactional
    public void excluir(DosesSemen dosesSemen) {
        Objects.requireNonNull(dosesSemen.getId());
        repository.delete(dosesSemen);
    }

    public void validar(DosesSemen dosesSemen) {
        if (dosesSemen.getMachoReprodutor() == null) {
            throw new RegraNegocioException("Macho reprodutor inválido");
        }
        if (dosesSemen.getSemenSexado() == null) {
            throw new RegraNegocioException("Sêmen sexado inválido");
        }
        if (dosesSemen.getNDoses() == null || dosesSemen.getNDoses() <= 0) {
            throw new RegraNegocioException("Número de doses inválido");
        }
        if (dosesSemen.getPrecoDose() == null || dosesSemen.getPrecoDose() < 0) {
            throw new RegraNegocioException("Preço da dose inválido");
        }
    }
}
