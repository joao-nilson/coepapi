package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MeioDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Meio;
import com.example.sgpoepapi.service.MeioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/meios")
@RequiredArgsConstructor
@CrossOrigin
public class MeioController {

    private final MeioService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Meio> meios = service.getMeios();
        return ResponseEntity.ok(meios.stream().map(MeioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Meio> meio = service.getMeioById(id);
        if (meio.isEmpty()) {
            return new ResponseEntity<>("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(meio.map(MeioDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody MeioDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Meio meio = modelMapper.map(dto, Meio.class);
            meio = service.salvar(meio);
            return new ResponseEntity<>(meio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody MeioDTO dto) {
        if (service.getMeioById(id).isEmpty()) {
            return new ResponseEntity<>("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            Meio meio = modelMapper.map(dto, Meio.class);
            meio.setId(id);
            service.salvar(meio);
            return ResponseEntity.ok(meio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Meio> meio = service.getMeioById(id);
        if (meio.isEmpty()) {
            return new ResponseEntity<>("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(meio.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
