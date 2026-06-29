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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/oocitosclassificacao")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Classificações de Oócito", description = "API de gerenciamento de classificações de oócito")
public class OocitoClassificacaoController {

    private final OocitoClassificacaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<OocitoClassificacao> classificacoes = service.getOocitosClassificacao();
        return ResponseEntity.ok(classificacoes.stream().map(OocitoClassificacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de classificação de oócito pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Classificação de oócito encontrada"),
            @ApiResponse(responseCode = "404", description = "Classificação de oócito não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da classificação de oócito") @PathVariable("id") Long id) {
        Optional<OocitoClassificacao> classificacao = service.getOocitoClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classificacao.map(OocitoClassificacaoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de classificação de oócito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Classificação de oócito salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody OocitoClassificacaoDTO dto) {
        try {
            OocitoClassificacao classificacao = converter(dto);
            classificacao = service.salvar(classificacao);
            return new ResponseEntity(classificacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de classificação de oócito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Classificação de oócito atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Classificação de oócito não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da classificação de oócito") @PathVariable("id") Long id, @RequestBody OocitoClassificacaoDTO dto) {
        if (!service.getOocitoClassificacaoById(id).isPresent()) {
            return new ResponseEntity("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            OocitoClassificacao classificacao = converter(dto);
            classificacao.setId(id);
            service.salvar(classificacao);
            return ResponseEntity.ok(classificacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de classificação de oócito")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Classificação de oócito excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Classificação de oócito não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da classificação de oócito") @PathVariable("id") Long id) {
        Optional<OocitoClassificacao> classificacao = service.getOocitoClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Classificação de oócito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(classificacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public OocitoClassificacao converter(OocitoClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        OocitoClassificacao classificacao = modelMapper.map(dto, OocitoClassificacao.class);
        return classificacao;
    }
}
