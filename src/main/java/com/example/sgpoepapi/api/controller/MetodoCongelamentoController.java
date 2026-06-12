package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MetodoCongelamentoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import com.example.sgpoepapi.service.MetodoCongelamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metodoscongelamento")
@RequiredArgsConstructor
@CrossOrigin
public class MetodoCongelamentoController {

    private final MetodoCongelamentoService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<MetodoCongelamento> metodos = service.getMetodosCongelamento();
        return ResponseEntity.ok(metodos.stream().map(MetodoCongelamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<MetodoCongelamento> metodo = service.getMetodoCongelamentoById(id);
        if (metodo.isEmpty()) {
            return new ResponseEntity<>("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(metodo.map(MetodoCongelamentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody MetodoCongelamentoDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            MetodoCongelamento metodo = modelMapper.map(dto, MetodoCongelamento.class);
            metodo = service.salvar(metodo);
            return new ResponseEntity<>(metodo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody MetodoCongelamentoDTO dto) {
        if (service.getMetodoCongelamentoById(id).isEmpty()) {
            return new ResponseEntity<>("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            MetodoCongelamento metodo = modelMapper.map(dto, MetodoCongelamento.class);
            metodo.setId(id);
            service.salvar(metodo);
            return ResponseEntity.ok(metodo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<MetodoCongelamento> metodo = service.getMetodoCongelamentoById(id);
        if (metodo.isEmpty()) {
            return new ResponseEntity<>("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(metodo.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
