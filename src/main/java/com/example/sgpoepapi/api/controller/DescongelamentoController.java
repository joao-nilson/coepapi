package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.DescongelamentoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Descongelamento;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import com.example.sgpoepapi.service.DescongelamentoService;
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
@RequestMapping("/api/v1/descongelamentos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Descongelamentos", description = "API de gerenciamento de descongelamentos")
public class DescongelamentoController {

    private final DescongelamentoService service;
    private final EmbriaoService embriaoService;
    private final MetodoCongelamentoService metodoCongelamentoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Descongelamento> descongelamentos = service.getDescongelamentos();
        return ResponseEntity.ok(descongelamentos.stream().map(DescongelamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de descongelamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Descongelamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Descongelamento não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do descongelamento") @PathVariable("id") Long id) {
        Optional<Descongelamento> descongelamento = service.getDescongelamentoById(id);
        if (!descongelamento.isPresent()) {
            return new ResponseEntity("Descongelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(descongelamento.map(DescongelamentoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de descongelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Descongelamento salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody DescongelamentoDTO dto) {
        try {
            Descongelamento descongelamento = converter(dto);
            descongelamento = service.salvar(descongelamento);
            return new ResponseEntity(descongelamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de descongelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Descongelamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Descongelamento não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do descongelamento") @PathVariable("id") Long id, @RequestBody DescongelamentoDTO dto) {
        if (!service.getDescongelamentoById(id).isPresent()) {
            return new ResponseEntity("Descongelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Descongelamento descongelamento = converter(dto);
            descongelamento.setId(id);
            service.salvar(descongelamento);
            return ResponseEntity.ok(descongelamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de descongelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Descongelamento excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Descongelamento não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do descongelamento") @PathVariable("id") Long id) {
        Optional<Descongelamento> descongelamento = service.getDescongelamentoById(id);
        if (!descongelamento.isPresent()) {
            return new ResponseEntity("Descongelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(descongelamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Descongelamento converter(DescongelamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Descongelamento descongelamento = modelMapper.map(dto, Descongelamento.class);
        if (dto.getEmbriaoId() != null) {
            Optional<Embriao> embriao = embriaoService.getEmbriaoById(dto.getEmbriaoId());
            descongelamento.setEmbriao(embriao.orElse(null));
        }
        if (dto.getMetodoId() != null) {
            Optional<MetodoCongelamento> metodo = metodoCongelamentoService.getMetodoCongelamentoById(dto.getMetodoId());
            descongelamento.setMetodo(metodo.orElse(null));
        }
        return descongelamento;
    }
}
