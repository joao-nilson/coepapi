package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.CongelamentoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Congelamento;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import com.example.sgpoepapi.service.CongelamentoService;
import com.example.sgpoepapi.service.EmbriaoService;
import com.example.sgpoepapi.service.MetodoCongelamentoService;
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
@RequestMapping("/api/v1/congelamentos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Congelamentos", description = "API de gerenciamento de congelamentos")
public class CongelamentoController {

    private final CongelamentoService service;
    private final EmbriaoService embriaoService;
    private final MetodoCongelamentoService metodoCongelamentoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Congelamento> congelamentos = service.getCongelamentos();
        return ResponseEntity.ok(congelamentos.stream().map(CongelamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de congelamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Congelamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Congelamento não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do congelamento") @PathVariable("id") Long id) {
        Optional<Congelamento> congelamento = service.getCongelamentoById(id);
        if (!congelamento.isPresent()) {
            return new ResponseEntity("Congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(congelamento.map(CongelamentoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Congelamento salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody CongelamentoDTO dto) {
        try {
            Congelamento congelamento = converter(dto);
            congelamento = service.salvar(congelamento);
            return new ResponseEntity(congelamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Congelamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Congelamento não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do congelamento") @PathVariable("id") Long id, @RequestBody CongelamentoDTO dto) {
        if (!service.getCongelamentoById(id).isPresent()) {
            return new ResponseEntity("Congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Congelamento congelamento = converter(dto);
            congelamento.setId(id);
            service.salvar(congelamento);
            return ResponseEntity.ok(congelamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Congelamento excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Congelamento não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do congelamento") @PathVariable("id") Long id) {
        Optional<Congelamento> congelamento = service.getCongelamentoById(id);
        if (!congelamento.isPresent()) {
            return new ResponseEntity("Congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(congelamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Congelamento converter(CongelamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Congelamento congelamento = modelMapper.map(dto, Congelamento.class);
        if (dto.getEmbriaoId() != null) {
            Optional<Embriao> embriao = embriaoService.getEmbriaoById(dto.getEmbriaoId());
            congelamento.setEmbriao(embriao.orElse(null));
        }
        if (dto.getMetodoId() != null) {
            Optional<MetodoCongelamento> metodo = metodoCongelamentoService.getMetodoCongelamentoById(dto.getMetodoId());
            congelamento.setMetodo(metodo.orElse(null));
        }
        return congelamento;
    }
}
