package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.OocitoClassificacaoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.OocitoClassificacao;
import com.example.sgpoepapi.service.OocitoClassificacaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/oocitosclassificacao")
@RequiredArgsConstructor
@CrossOrigin
public class OocitoClassificacaoController {

    private final OocitoClassificacaoService service;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<OocitoClassificacao> classificacoes = service.getOocitosClassificacao();
        return ResponseEntity.ok(classificacoes.stream().map(OocitoClassificacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<OocitoClassificacao> classificacao = service.getOocitoClassificacaoById(id);
        if (classificacao.isEmpty()) {
            return new ResponseEntity<>("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classificacao.map(OocitoClassificacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody OocitoClassificacaoDTO dto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            OocitoClassificacao classificacao = modelMapper.map(dto, OocitoClassificacao.class);
            classificacao = service.salvar(classificacao);
            return new ResponseEntity<>(classificacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody OocitoClassificacaoDTO dto) {
        if (service.getOocitoClassificacaoById(id).isEmpty()) {
            return new ResponseEntity<>("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            OocitoClassificacao classificacao = modelMapper.map(dto, OocitoClassificacao.class);
            classificacao.setId(id);
            service.salvar(classificacao);
            return ResponseEntity.ok(classificacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<OocitoClassificacao> classificacao = service.getOocitoClassificacaoById(id);
        if (classificacao.isEmpty()) {
            return new ResponseEntity<>("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(classificacao.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
