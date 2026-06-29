package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.SincronizacaoDTO;
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
@RequestMapping("/api/v1/sincronizacoes")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Sincronizações", description = "API de gerenciamento de sincronizações")
public class SincronizacaoController {

    private final SincronizacaoService service;
    private final ProjetoService projetoService;
    private final ClienteService clienteService;
    private final PrestadoraServicoService prestadoraServicoService;
    private final MatrizReceptoraService matrizReceptoraService;

    @GetMapping()
    public ResponseEntity get() {
        List<Sincronizacao> sincronizacoes = service.getSincronizacoes();
        return ResponseEntity.ok(sincronizacoes.stream().map(SincronizacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de sincronização pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sincronização encontrada"),
            @ApiResponse(responseCode = "404", description = "Sincronização não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da sincronização") @PathVariable("id") Long id) {
        Optional<Sincronizacao> sincronizacao = service.getSincronizacaoById(id);
        if (!sincronizacao.isPresent()) {
            return new ResponseEntity("Sincronização não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sincronizacao.map(SincronizacaoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de sincronização")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sincronização salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody SincronizacaoDTO dto) {
        try {
            Sincronizacao sincronizacao = converter(dto);
            sincronizacao = service.salvar(sincronizacao);
            return new ResponseEntity(sincronizacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de sincronização")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sincronização atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Sincronização não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da sincronização") @PathVariable("id") Long id, @RequestBody SincronizacaoDTO dto) {
        if (!service.getSincronizacaoById(id).isPresent()) {
            return new ResponseEntity("Sincronização não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sincronizacao sincronizacao = converter(dto);
            sincronizacao.setId(id);
            service.salvar(sincronizacao);
            return ResponseEntity.ok(sincronizacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de sincronização")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sincronização excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Sincronização não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da sincronização") @PathVariable("id") Long id) {
        Optional<Sincronizacao> sincronizacao = service.getSincronizacaoById(id);
        if (!sincronizacao.isPresent()) {
            return new ResponseEntity("Sincronização não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sincronizacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sincronizacao converter(SincronizacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sincronizacao sincronizacao = modelMapper.map(dto, Sincronizacao.class);
        if (dto.getProjetoId() != null) {
            Optional<Projeto> projeto = projetoService.getProjetoById(dto.getProjetoId());
            sincronizacao.setProjeto(projeto.orElse(null));
        }
        if (dto.getProprietarioClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioClienteId());
            sincronizacao.setProprietarioCliente(cliente.orElse(null));
        }
        if (dto.getProprietarioPrestadoraId() != null) {
            Optional<PrestadoraServico> prestadora = prestadoraServicoService.getPrestadoraServicoById(dto.getProprietarioPrestadoraId());
            sincronizacao.setProprietarioPrestadora(prestadora.orElse(null));
        }
        if (dto.getReceptorasIds() != null) {
            List<MatrizReceptora> receptoras = dto.getReceptorasIds().stream()
                    .map(id -> matrizReceptoraService.getMatrizReceptoraById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            sincronizacao.setReceptoras(receptoras);
        }
        if (dto.getReceptorasSincronizadasIds() != null) {
            List<MatrizReceptora> receptorasSincronizadas = dto.getReceptorasSincronizadasIds().stream()
                    .map(id -> matrizReceptoraService.getMatrizReceptoraById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            sincronizacao.setReceptorasSincronizadas(receptorasSincronizadas);
        }
        return sincronizacao;
    }
}
