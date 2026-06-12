package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.FuncionarioDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Funcionario;
import com.example.sgpoepapi.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
@CrossOrigin
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (funcionario.isEmpty()) {
            return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody FuncionarioDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto) {
        if (service.getFuncionarioById(id).isEmpty()) {
            return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
            funcionario.setId(id);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (funcionario.isEmpty()) {
            return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
