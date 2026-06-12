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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sincronizacoes")
@RequiredArgsConstructor
@CrossOrigin
public class SincronizacaoController {

    private final SincronizacaoService service;
    private final ProjetoService projetoService;
    private final ClienteService clienteService;
    private final PrestadoraServicoService prestadoraServicoService;
    private final MatrizReceptoraService matrizReceptoraService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Sincronizacao> sincronizacoes = service.getSincronizacoes();
        return ResponseEntity.ok(sincronizacoes.stream().map(SincronizacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Sincronizacao> sincronizacao = service.getSincronizacaoById(id);
        if (sincronizacao.isEmpty()) {
            return new ResponseEntity<>("Sincronização não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sincronizacao.map(SincronizacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody SincronizacaoDTO dto) {
        try {
            Sincronizacao sincronizacao = converter(dto);
            sincronizacao = service.salvar(sincronizacao);
            return new ResponseEntity<>(sincronizacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody SincronizacaoDTO dto) {
        if (service.getSincronizacaoById(id).isEmpty()) {
            return new ResponseEntity<>("Sincronização não encontrada", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Sincronizacao> sincronizacao = service.getSincronizacaoById(id);
        if (sincronizacao.isEmpty()) {
            return new ResponseEntity<>("Sincronização não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sincronizacao.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
