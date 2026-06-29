package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MeioDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Meio;
import com.example.sgpoepapi.service.MeioService;
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
@RequestMapping("/api/v1/meios")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Meios", description = "API de gerenciamento de meios")
public class MeioController {

    private final MeioService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Meio> meios = service.getMeios();
        return ResponseEntity.ok(meios.stream().map(MeioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de meio pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meio encontrado"),
            @ApiResponse(responseCode = "404", description = "Meio não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do meio") @PathVariable("id") Long id) {
        Optional<Meio> meio = service.getMeioById(id);
        if (!meio.isPresent()) {
            return new ResponseEntity("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(meio.map(MeioDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de meio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meio salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody MeioDTO dto) {
        try {
            Meio meio = converter(dto);
            meio = service.salvar(meio);
            return new ResponseEntity(meio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de meio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meio atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Meio não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do meio") @PathVariable("id") Long id, @RequestBody MeioDTO dto) {
        if (!service.getMeioById(id).isPresent()) {
            return new ResponseEntity("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Meio meio = converter(dto);
            meio.setId(id);
            service.salvar(meio);
            return ResponseEntity.ok(meio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de meio")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meio excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Meio não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do meio") @PathVariable("id") Long id) {
        Optional<Meio> meio = service.getMeioById(id);
        if (!meio.isPresent()) {
            return new ResponseEntity("Meio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(meio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Meio converter(MeioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Meio meio = modelMapper.map(dto, Meio.class);
        return meio;
    }
}
