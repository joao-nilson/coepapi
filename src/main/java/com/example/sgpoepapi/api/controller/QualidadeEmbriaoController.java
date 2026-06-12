package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.QualidadeEmbriaoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.QualidadeEmbriao;
import com.example.sgpoepapi.service.QualidadeEmbriaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/qualidadesembriao")
@RequiredArgsConstructor
@CrossOrigin
public class QualidadeEmbriaoController {

    private final QualidadeEmbriaoService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<QualidadeEmbriao> qualidades = service.getQualidadesEmbriao();
        return ResponseEntity.ok(qualidades.stream().map(QualidadeEmbriaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<QualidadeEmbriao> qualidade = service.getQualidadeEmbriaoById(id);
        if (qualidade.isEmpty()) {
            return new ResponseEntity<>("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(qualidade.map(QualidadeEmbriaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody QualidadeEmbriaoDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            QualidadeEmbriao qualidade = modelMapper.map(dto, QualidadeEmbriao.class);
            qualidade = service.salvar(qualidade);
            return new ResponseEntity<>(qualidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody QualidadeEmbriaoDTO dto) {
        if (service.getQualidadeEmbriaoById(id).isEmpty()) {
            return new ResponseEntity<>("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            QualidadeEmbriao qualidade = modelMapper.map(dto, QualidadeEmbriao.class);
            qualidade.setId(id);
            service.salvar(qualidade);
            return ResponseEntity.ok(qualidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<QualidadeEmbriao> qualidade = service.getQualidadeEmbriaoById(id);
        if (qualidade.isEmpty()) {
            return new ResponseEntity<>("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(qualidade.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
