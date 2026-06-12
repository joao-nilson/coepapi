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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transferenciaembrioes")
@RequiredArgsConstructor
@CrossOrigin
public class TransferenciaEmbrioesController {

    private final TransferenciaEmbrioesService service;
    private final ProjetoService projetoService;
    private final ClienteService clienteService;
    private final PrestadoraServicoService prestadoraServicoService;
    private final EmbriaoService embriaoService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<TransferenciaEmbrioes> transferencias = service.getTransferenciasEmbrioes();
        return ResponseEntity.ok(transferencias.stream().map(TransferenciaEmbrioesDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<TransferenciaEmbrioes> transferencia = service.getTransferenciaEmbrioesById(id);
        if (transferencia.isEmpty()) {
            return new ResponseEntity<>("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(transferencia.map(TransferenciaEmbrioesDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody TransferenciaEmbrioesDTO dto) {
        try {
            TransferenciaEmbrioes transferencia = converter(dto);
            transferencia = service.salvar(transferencia);
            return new ResponseEntity<>(transferencia, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody TransferenciaEmbrioesDTO dto) {
        if (service.getTransferenciaEmbrioesById(id).isEmpty()) {
            return new ResponseEntity<>("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<TransferenciaEmbrioes> transferencia = service.getTransferenciaEmbrioesById(id);
        if (transferencia.isEmpty()) {
            return new ResponseEntity<>("Transferência de embriões não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(transferencia.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
