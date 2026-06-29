package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MatrizDoadoraDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.entity.MatrizDoadora;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.ClienteService;
import com.example.sgpoepapi.service.MatrizDoadoraService;
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
@RequestMapping("/api/v1/matrizesdoadoras")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Matrizes Doadoras", description = "API de gerenciamento de matrizes doadoras")
public class MatrizDoadoraController {

    private final MatrizDoadoraService service;
    private final RacaService racaService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity get() {
        List<MatrizDoadora> doadoras = service.getMatrizesDoadadoras();
        return ResponseEntity.ok(doadoras.stream().map(MatrizDoadoraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de matriz doadora pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matriz doadora encontrada"),
            @ApiResponse(responseCode = "404", description = "Matriz doadora não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da matriz doadora") @PathVariable("id") Long id) {
        Optional<MatrizDoadora> doadora = service.getMatrizDoadoraById(id);
        if (!doadora.isPresent()) {
            return new ResponseEntity("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(doadora.map(MatrizDoadoraDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de matriz doadora")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Matriz doadora salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody MatrizDoadoraDTO dto) {
        try {
            MatrizDoadora doadora = converter(dto);
            doadora = service.salvar(doadora);
            return new ResponseEntity(doadora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de matriz doadora")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matriz doadora atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Matriz doadora não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da matriz doadora") @PathVariable("id") Long id, @RequestBody MatrizDoadoraDTO dto) {
        if (!service.getMatrizDoadoraById(id).isPresent()) {
            return new ResponseEntity("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            MatrizDoadora doadora = converter(dto);
            doadora.setId(id);
            service.salvar(doadora);
            return ResponseEntity.ok(doadora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de matriz doadora")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Matriz doadora excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Matriz doadora não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da matriz doadora") @PathVariable("id") Long id) {
        Optional<MatrizDoadora> doadora = service.getMatrizDoadoraById(id);
        if (!doadora.isPresent()) {
            return new ResponseEntity("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(doadora.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MatrizDoadora converter(MatrizDoadoraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        MatrizDoadora doadora = modelMapper.map(dto, MatrizDoadora.class);
        if (dto.getRacaId() != null) {
            Optional<Raca> raca = racaService.getRacaById(dto.getRacaId());
            doadora.setRaca(raca.orElse(null));
        }
        if (dto.getProprietarioId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioId());
            doadora.setProprietario(cliente.orElse(null));
        }
        return doadora;
    }
}
