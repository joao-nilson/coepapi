package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.repository.ClienteRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRep repository;

    public ClienteService(ClienteRep repository) {
        this.repository = repository;
    }

    public List<Cliente> getClientes() {
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (cliente.getCPF() == null || cliente.getCPF().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (cliente.getEndereco() == null || cliente.getEndereco().trim().equals("")) {
            throw new RegraNegocioException("Endereço inválido");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }
}
