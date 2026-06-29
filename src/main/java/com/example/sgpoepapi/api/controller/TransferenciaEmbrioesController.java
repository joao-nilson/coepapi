package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.TransferenciaEmbrioesDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.*;
import com.example.sgpoepapi.service.*;
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
@RequestMapping("/api/v1/transferenciaembrioes")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Transferências de Embriões", description = "API de gerenciamento de transferências de embriões")
public class TransferenciaEmbrioesController {

    private final TransferenciaEmbrioesService service;
    private final ProjetoService projetoService;
    private final ClienteService clienteService;
    private final PrestadoraServicoService prestadoraServicoService;
    private final EmbriaoService embriaoService;

    @GetMapping()
    public ResponseEntity get() {
        List<TransferenciaEmbrioes> transferencias = service.getTransferenciasEmbrioes();
        return ResponseEntity.ok(transferencias.stream().map(TransferenciaEmbrioesDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de transferência de embriões pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferência de embriões encontrada"),
            @ApiResponse(responseCode = "404", description = "Transferência de embriões não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da transferência de embriões") @PathVariable("id") Long id) {
        Optional<TransferenciaEmbrioes> transferencia = service.getTransferenciaEmbrioesById(id);
        if (!transferencia.isPresent()) {
            return new ResponseEntity("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(transferencia.map(TransferenciaEmbrioesDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de transferência de embriões")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transferência de embriões salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody TransferenciaEmbrioesDTO dto) {
        try {
            TransferenciaEmbrioes transferencia = converter(dto);
            transferencia = service.salvar(transferencia);
            return new ResponseEntity(transferencia, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de transferência de embriões")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferência de embriões atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Transferência de embriões não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da transferência de embriões") @PathVariable("id") Long id, @RequestBody TransferenciaEmbrioesDTO dto) {
        if (!service.getTransferenciaEmbrioesById(id).isPresent()) {
            return new ResponseEntity("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            TransferenciaEmbrioes transferencia = converter(dto);
            transferencia.setId(id);
            service.salvar(transferencia);
            return ResponseEntity.ok(transferencia);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de transferência de embriões")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transferência de embriões excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Transferência de embriões não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da transferência de embriões") @PathVariable("id") Long id) {
        Optional<TransferenciaEmbrioes> transferencia = service.getTransferenciaEmbrioesById(id);
        if (!transferencia.isPresent()) {
            return new ResponseEntity("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(transferencia.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TransferenciaEmbrioes converter(TransferenciaEmbrioesDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TransferenciaEmbrioes transferencia = modelMapper.map(dto, TransferenciaEmbrioes.class);
        if (dto.getProjetoId() != null) {
            Optional<Projeto> projeto = projetoService.getProjetoById(dto.getProjetoId());
            transferencia.setProjeto(projeto.orElse(null));
        }
        if (dto.getTecnicoClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getTecnicoClienteId());
            transferencia.setTecnicoCliente(cliente.orElse(null));
        }
        if (dto.getTecnicoPrestadoraId() != null) {
            Optional<PrestadoraServico> prestadora = prestadoraServicoService.getPrestadoraServicoById(dto.getTecnicoPrestadoraId());
            transferencia.setTecnicoPrestadora(prestadora.orElse(null));
        }
        if (dto.getEmbrioesIds() != null) {
            List<Embriao> embrioes = dto.getEmbrioesIds().stream()
                    .map(id -> embriaoService.getEmbriaoById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            transferencia.setEmbrioes(embrioes);
        }
        return transferencia;
    }
}
