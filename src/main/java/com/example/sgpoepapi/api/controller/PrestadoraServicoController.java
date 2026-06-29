package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.PrestadoraServicoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.PrestadoraServico;
import com.example.sgpoepapi.service.PrestadoraServicoService;
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
@RequestMapping("/api/v1/prestadorasservico")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Prestadoras de Serviço", description = "API de gerenciamento de prestadoras de serviço")
public class PrestadoraServicoController {

    private final PrestadoraServicoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<PrestadoraServico> prestadoras = service.getPrestadorasServico();
        return ResponseEntity.ok(prestadoras.stream().map(PrestadoraServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de prestadora de serviço pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prestadora de serviço encontrada"),
            @ApiResponse(responseCode = "404", description = "Prestadora de serviço não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da prestadora de serviço") @PathVariable("id") Long id) {
        Optional<PrestadoraServico> prestadora = service.getPrestadoraServicoById(id);
        if (!prestadora.isPresent()) {
            return new ResponseEntity("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(prestadora.map(PrestadoraServicoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de prestadora de serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prestadora de serviço salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody PrestadoraServicoDTO dto) {
        try {
            PrestadoraServico prestadora = converter(dto);
            prestadora = service.salvar(prestadora);
            return new ResponseEntity(prestadora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de prestadora de serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prestadora de serviço atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Prestadora de serviço não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da prestadora de serviço") @PathVariable("id") Long id, @RequestBody PrestadoraServicoDTO dto) {
        if (!service.getPrestadoraServicoById(id).isPresent()) {
            return new ResponseEntity("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            PrestadoraServico prestadora = converter(dto);
            prestadora.setId(id);
            service.salvar(prestadora);
            return ResponseEntity.ok(prestadora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de prestadora de serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prestadora de serviço excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Prestadora de serviço não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da prestadora de serviço") @PathVariable("id") Long id) {
        Optional<PrestadoraServico> prestadora = service.getPrestadoraServicoById(id);
        if (!prestadora.isPresent()) {
            return new ResponseEntity("Prestadora de serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(prestadora.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PrestadoraServico converter(PrestadoraServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PrestadoraServico prestadora = modelMapper.map(dto, PrestadoraServico.class);
        return prestadora;
    }
}
