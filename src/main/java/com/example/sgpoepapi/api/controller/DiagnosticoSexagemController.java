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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/diagnosticossexagem")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Diagnósticos de Sexagem", description = "API de gerenciamento de diagnósticos de sexagem")
public class DiagnosticoSexagemController {

    private final DiagnosticoSexagemService service;
    private final EmbriaoService embriaoService;

    @GetMapping()
    public ResponseEntity get() {
        List<DiagnosticoSexagem> diagnosticos = service.getDiagnosticosSexagem();
        return ResponseEntity.ok(diagnosticos.stream().map(DiagnosticoSexagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de diagnóstico de sexagem pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Diagnóstico de sexagem encontrado"),
            @ApiResponse(responseCode = "404", description = "Diagnóstico de sexagem não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do diagnóstico de sexagem") @PathVariable("id") Long id) {
        Optional<DiagnosticoSexagem> diagnostico = service.getDiagnosticoSexagemById(id);
        if (!diagnostico.isPresent()) {
            return new ResponseEntity("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(diagnostico.map(DiagnosticoSexagemDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de diagnóstico de sexagem")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Diagnóstico de sexagem salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody DiagnosticoSexagemDTO dto) {
        try {
            DiagnosticoSexagem diagnostico = converter(dto);
            diagnostico = service.salvar(diagnostico);
            return new ResponseEntity(diagnostico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de diagnóstico de sexagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Diagnóstico de sexagem atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Diagnóstico de sexagem não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do diagnóstico de sexagem") @PathVariable("id") Long id, @RequestBody DiagnosticoSexagemDTO dto) {
        if (!service.getDiagnosticoSexagemById(id).isPresent()) {
            return new ResponseEntity("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
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
    @Operation(summary = "Excluir um registro de diagnóstico de sexagem")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Diagnóstico de sexagem excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Diagnóstico de sexagem não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do diagnóstico de sexagem") @PathVariable("id") Long id) {
        Optional<DiagnosticoSexagem> diagnostico = service.getDiagnosticoSexagemById(id);
        if (!diagnostico.isPresent()) {
            return new ResponseEntity("Diagnóstico de sexagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(diagnostico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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
