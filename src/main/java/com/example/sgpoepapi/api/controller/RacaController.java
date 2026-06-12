package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.RacaDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.RacaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/racas")
@RequiredArgsConstructor
@CrossOrigin
public class RacaController {

    private final RacaService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Raca> racas = service.getRacas();
        return ResponseEntity.ok(racas.stream().map(RacaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Raca> raca = service.getRacaById(id);
        if (raca.isEmpty()) {
            return new ResponseEntity<>("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(raca.map(RacaDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody RacaDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Raca raca = modelMapper.map(dto, Raca.class);
            raca = service.salvar(raca);
            return new ResponseEntity<>(raca, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody RacaDTO dto) {
        if (service.getRacaById(id).isEmpty()) {
            return new ResponseEntity<>("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            Raca raca = modelMapper.map(dto, Raca.class);
            raca.setId(id);
            service.salvar(raca);
            return ResponseEntity.ok(raca);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Raca> raca = service.getRacaById(id);
        if (raca.isEmpty()) {
            return new ResponseEntity<>("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(raca.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
