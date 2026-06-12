package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.DiagnosticoGestacaoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DiagnosticoGestacao;
import com.example.sgpoepapi.model.entity.MatrizReceptora;
import com.example.sgpoepapi.service.DiagnosticoGestacaoService;
import com.example.sgpoepapi.service.MatrizReceptoraService;
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
@RequestMapping("/api/v1/diagnosticosgestacao")
@RequiredArgsConstructor
@CrossOrigin
public class DiagnosticoGestacaoController {

    private final DiagnosticoGestacaoService service;
    private final MatrizReceptoraService matrizReceptoraService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<DiagnosticoGestacao> diagnosticos = service.getDiagnosticosGestacao();
        return ResponseEntity.ok(diagnosticos.stream().map(DiagnosticoGestacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<DiagnosticoGestacao> diagnostico = service.getDiagnosticoGestacaoById(id);
        if (diagnostico.isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de gestação não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(diagnostico.map(DiagnosticoGestacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody DiagnosticoGestacaoDTO dto) {
        try {
            DiagnosticoGestacao diagnostico = converter(dto);
            diagnostico = service.salvar(diagnostico);
            return new ResponseEntity<>(diagnostico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody DiagnosticoGestacaoDTO dto) {
        if (service.getDiagnosticoGestacaoById(id).isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de gestação não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            DiagnosticoGestacao diagnostico = converter(dto);
            diagnostico.setId(id);
            service.salvar(diagnostico);
            return ResponseEntity.ok(diagnostico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<DiagnosticoGestacao> diagnostico = service.getDiagnosticoGestacaoById(id);
        if (diagnostico.isEmpty()) {
            return new ResponseEntity<>("Diagnóstico de gestação não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(diagnostico.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public DiagnosticoGestacao converter(DiagnosticoGestacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        DiagnosticoGestacao diagnostico = modelMapper.map(dto, DiagnosticoGestacao.class);
        if (dto.getPrenhasIds() != null) {
            List<MatrizReceptora> prenhas = dto.getPrenhasIds().stream()
                    .map(id -> matrizReceptoraService.getMatrizReceptoraById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            diagnostico.setPrenhas(prenhas);
        }
        if (dto.getVaziasIds() != null) {
            List<MatrizReceptora> vazias = dto.getVaziasIds().stream()
                    .map(id -> matrizReceptoraService.getMatrizReceptoraById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            diagnostico.setVazias(vazias);
        }
        return diagnostico;
    }
}
