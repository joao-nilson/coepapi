package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.EmbriaoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.model.entity.QualidadeEmbriao;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.EmbriaoService;
import com.example.sgpoepapi.service.QualidadeEmbriaoService;
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
@RequestMapping("/api/v1/embrioes")
@RequiredArgsConstructor
@CrossOrigin
public class EmbriaoController {

    private final EmbriaoService service;
    private final RacaService racaService;
    private final QualidadeEmbriaoService qualidadeEmbriaoService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Embriao> embrioes = service.getEmbrioes();
        return ResponseEntity.ok(embrioes.stream().map(EmbriaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Embriao> embriao = service.getEmbriaoById(id);
        if (embriao.isEmpty()) {
            return new ResponseEntity<>("Embrião não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(embriao.map(EmbriaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody EmbriaoDTO dto) {
        try {
            Embriao embriao = converter(dto);
            embriao = service.salvar(embriao);
            return new ResponseEntity<>(embriao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody EmbriaoDTO dto) {
        if (service.getEmbriaoById(id).isEmpty()) {
            return new ResponseEntity<>("Embrião não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Embriao embriao = converter(dto);
            embriao.setId(id);
            service.salvar(embriao);
            return ResponseEntity.ok(embriao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Embriao> embriao = service.getEmbriaoById(id);
        if (embriao.isEmpty()) {
            return new ResponseEntity<>("Embrião não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(embriao.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Embriao converter(EmbriaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Embriao embriao = modelMapper.map(dto, Embriao.class);
        if (dto.getRacaId() != null) {
            Optional<Raca> raca = racaService.getRacaById(dto.getRacaId());
            embriao.setRaca(raca.orElse(null));
        }
        if (dto.getQualidadeId() != null) {
            Optional<QualidadeEmbriao> qualidade = qualidadeEmbriaoService.getQualidadeEmbriaoById(dto.getQualidadeId());
            embriao.setQualidade(qualidade.orElse(null));
        }
        return embriao;
    }
}
