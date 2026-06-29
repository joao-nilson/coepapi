package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.RacaDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.RacaService;
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
@RequestMapping("/api/v1/racas")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Raças", description = "API de gerenciamento de raças")
public class RacaController {

    private final RacaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Raca> racas = service.getRacas();
        return ResponseEntity.ok(racas.stream().map(RacaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de raça pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Raça encontrada"),
            @ApiResponse(responseCode = "404", description = "Raça não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da raça") @PathVariable("id") Long id) {
        Optional<Raca> raca = service.getRacaById(id);
        if (!raca.isPresent()) {
            return new ResponseEntity("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(raca.map(RacaDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de raça")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Raça salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody RacaDTO dto) {
        try {
            Raca raca = converter(dto);
            raca = service.salvar(raca);
            return new ResponseEntity(raca, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de raça")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Raça atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Raça não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da raça") @PathVariable("id") Long id, @RequestBody RacaDTO dto) {
        if (!service.getRacaById(id).isPresent()) {
            return new ResponseEntity("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Raca raca = converter(dto);
            raca.setId(id);
            service.salvar(raca);
            return ResponseEntity.ok(raca);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de raça")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Raça excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Raça não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da raça") @PathVariable("id") Long id) {
        Optional<Raca> raca = service.getRacaById(id);
        if (!raca.isPresent()) {
            return new ResponseEntity("Raça não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(raca.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Raca converter(RacaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Raca raca = modelMapper.map(dto, Raca.class);
        return raca;
    }
}
