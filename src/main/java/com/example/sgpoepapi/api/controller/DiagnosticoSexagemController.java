package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.DiagnosticoSexagemDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DiagnosticoSexagem;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.service.DiagnosticoSexagemService;
import com.example.sgpoepapi.service.EmbriaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/diagnosticossexagem")
@RequiredArgsConstructor
@CrossOrigin
public class DiagnosticoSexagemController {

    private final DiagnosticoSexagemService service;
    private final EmbriaoService embriaoService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<DiagnosticoSexagem> diagnosticos = service.getDiagnosticosSexagem();
        return ResponseEntity.ok(diagnosticos.stream().map(DiagnosticoSexagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<DiagnosticoSexagem> diagnostico = service.getDiagnosticoSexagemById(id);
        if (diagnostico.isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(diagnostico.map(DiagnosticoSexagemDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody DiagnosticoSexagemDTO dto) {
        try {
            DiagnosticoSexagem diagnostico = converter(dto);
            diagnostico = service.salvar(diagnostico);
            return new ResponseEntity<>(diagnostico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody DiagnosticoSexagemDTO dto) {
        if (service.getDiagnosticoSexagemById(id).isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            DiagnosticoSexagem diagnostico = converter(dto);
            diagnostico.setId(id);
            service.salvar(diagnostico);
            return ResponseEntity.ok(diagnostico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<DiagnosticoSexagem> diagnostico = service.getDiagnosticoSexagemById(id);
        if (diagnostico.isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(diagnostico.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public DiagnosticoSexagem converter(DiagnosticoSexagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        DiagnosticoSexagem diagnostico = modelMapper.map(dto, DiagnosticoSexagem.class);
        if (dto.getMachosIds() != null) {
            List<Embriao> machos = dto.getMachosIds().stream()
                    .map(id -> embriaoService.getEmbriaoById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            diagnostico.setMachos(machos);
        }
        if (dto.getFemeasIds() != null) {
            List<Embriao> femeas = dto.getFemeasIds().stream()
                    .map(id -> embriaoService.getEmbriaoById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            diagnostico.setFemeas(femeas);
        }
        return diagnostico;
    }
}
