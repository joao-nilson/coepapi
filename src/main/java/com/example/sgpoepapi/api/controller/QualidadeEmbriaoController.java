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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/qualidadesembriao")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Qualidades de Embrião", description = "API de gerenciamento de qualidades de embrião")
public class QualidadeEmbriaoController {

    private final QualidadeEmbriaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<QualidadeEmbriao> qualidades = service.getQualidadesEmbriao();
        return ResponseEntity.ok(qualidades.stream().map(QualidadeEmbriaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de qualidade de embrião pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Qualidade de embrião encontrada"),
            @ApiResponse(responseCode = "404", description = "Qualidade de embrião não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da qualidade de embrião") @PathVariable("id") Long id) {
        Optional<QualidadeEmbriao> qualidade = service.getQualidadeEmbriaoById(id);
        if (!qualidade.isPresent()) {
            return new ResponseEntity("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(qualidade.map(QualidadeEmbriaoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de qualidade de embrião")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Qualidade de embrião salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody QualidadeEmbriaoDTO dto) {
        try {
            QualidadeEmbriao qualidade = converter(dto);
            qualidade = service.salvar(qualidade);
            return new ResponseEntity(qualidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de qualidade de embrião")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Qualidade de embrião atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Qualidade de embrião não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da qualidade de embrião") @PathVariable("id") Long id, @RequestBody QualidadeEmbriaoDTO dto) {
        if (!service.getQualidadeEmbriaoById(id).isPresent()) {
            return new ResponseEntity("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            QualidadeEmbriao qualidade = converter(dto);
            qualidade.setId(id);
            service.salvar(qualidade);
            return ResponseEntity.ok(qualidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de qualidade de embrião")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Qualidade de embrião excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Qualidade de embrião não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da qualidade de embrião") @PathVariable("id") Long id) {
        Optional<QualidadeEmbriao> qualidade = service.getQualidadeEmbriaoById(id);
        if (!qualidade.isPresent()) {
            return new ResponseEntity("Qualidade de embrião não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(qualidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public QualidadeEmbriao converter(QualidadeEmbriaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        QualidadeEmbriao qualidade = modelMapper.map(dto, QualidadeEmbriao.class);
        return qualidade;
    }
}
