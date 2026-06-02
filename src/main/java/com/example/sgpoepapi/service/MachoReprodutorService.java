package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MachoReprodutor;
import com.example.sgpoepapi.model.repository.MachoReprodutorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MachoReprodutorService {

    private MachoReprodutorRepositorio repository;

    public MachoReprodutorService(MachoReprodutorRepositorio repository) {
        this.repository = repository;
    }

    public List<MachoReprodutor> getMachosReprodutores() {
        return repository.findAll();
    }

    public Optional<MachoReprodutor> getMachoReprodutorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MachoReprodutor salvar(MachoReprodutor machoReprodutor) {
        validar(machoReprodutor);
        return repository.save(machoReprodutor);
    }

    @Transactional
    public void excluir(MachoReprodutor machoReprodutor) {
        Objects.requireNonNull(machoReprodutor.getId());
        repository.delete(machoReprodutor);
    }

    public void validar(MachoReprodutor machoReprodutor) {
        if (machoReprodutor.getNome() == null || machoReprodutor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (machoReprodutor.getRaca() == null) {
            throw new RegraNegocioException("Raça inválida");
        }
        if (machoReprodutor.getProprietario() == null) {
            throw new RegraNegocioException("Proprietário inválido");
        }
        if (machoReprodutor.getSemenSexado() == null) {
            throw new RegraNegocioException("Sêmen sexado inválido");
        }
        if (machoReprodutor.getPrecoDose() == null || machoReprodutor.getPrecoDose() < 0) {
            throw new RegraNegocioException("Preço da dose inválido");
        }
    }
}
