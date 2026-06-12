package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.PrestadoraServicoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.PrestadoraServico;
import com.example.sgpoepapi.service.PrestadoraServicoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prestadorasservico")
@RequiredArgsConstructor
@CrossOrigin
public class PrestadoraServicoController {

    private final PrestadoraServicoService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<PrestadoraServico> prestadoras = service.getPrestadorasServico();
        return ResponseEntity.ok(prestadoras.stream().map(PrestadoraServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<PrestadoraServico> prestadora = service.getPrestadoraServicoById(id);
        if (prestadora.isEmpty()) {
            return new ResponseEntity<>("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(prestadora.map(PrestadoraServicoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody PrestadoraServicoDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            PrestadoraServico prestadora = modelMapper.map(dto, PrestadoraServico.class);
            prestadora = service.salvar(prestadora);
            return new ResponseEntity<>(prestadora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody PrestadoraServicoDTO dto) {
        if (service.getPrestadoraServicoById(id).isEmpty()) {
            return new ResponseEntity<>("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            PrestadoraServico prestadora = modelMapper.map(dto, PrestadoraServico.class);
            prestadora.setId(id);
            service.salvar(prestadora);
            return ResponseEntity.ok(prestadora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<PrestadoraServico> prestadora = service.getPrestadoraServicoById(id);
        if (prestadora.isEmpty()) {
            return new ResponseEntity<>("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(prestadora.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
