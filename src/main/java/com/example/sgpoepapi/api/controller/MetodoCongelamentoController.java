package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MetodoCongelamentoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MetodoCongelamento;
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
@RequestMapping("/api/v1/metodoscongelamento")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Métodos de Congelamento", description = "API de gerenciamento de métodos de congelamento")
public class MetodoCongelamentoController {

    private final MetodoCongelamentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<MetodoCongelamento> metodos = service.getMetodosCongelamento();
        return ResponseEntity.ok(metodos.stream().map(MetodoCongelamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de método de congelamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Método de congelamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Método de congelamento não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do método de congelamento") @PathVariable("id") Long id) {
        Optional<MetodoCongelamento> metodo = service.getMetodoCongelamentoById(id);
        if (!metodo.isPresent()) {
            return new ResponseEntity("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(metodo.map(MetodoCongelamentoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de método de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Método de congelamento salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody MetodoCongelamentoDTO dto) {
        try {
            MetodoCongelamento metodo = converter(dto);
            metodo = service.salvar(metodo);
            return new ResponseEntity(metodo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de método de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Método de congelamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Método de congelamento não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do método de congelamento") @PathVariable("id") Long id, @RequestBody MetodoCongelamentoDTO dto) {
        if (!service.getMetodoCongelamentoById(id).isPresent()) {
            return new ResponseEntity("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            MetodoCongelamento metodo = converter(dto);
            metodo.setId(id);
            service.salvar(metodo);
            return ResponseEntity.ok(metodo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de método de congelamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Método de congelamento excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Método de congelamento não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do método de congelamento") @PathVariable("id") Long id) {
        Optional<MetodoCongelamento> metodo = service.getMetodoCongelamentoById(id);
        if (!metodo.isPresent()) {
            return new ResponseEntity("Método de congelamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(metodo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MetodoCongelamento converter(MetodoCongelamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        MetodoCongelamento metodo = modelMapper.map(dto, MetodoCongelamento.class);
        return metodo;
    }
}
