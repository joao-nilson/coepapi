package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.ClienteDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@CrossOrigin
public class ClienteController {

    private final ClienteService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (cliente.isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody ClienteDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Cliente cliente = modelMapper.map(dto, Cliente.class);
            cliente = service.salvar(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if (service.getClienteById(id).isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            Cliente cliente = modelMapper.map(dto, Cliente.class);
            cliente.setId(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (cliente.isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
