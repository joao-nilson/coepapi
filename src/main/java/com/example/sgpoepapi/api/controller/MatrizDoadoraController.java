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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/matrizesdoadoras")
@RequiredArgsConstructor
@CrossOrigin
public class MatrizDoadoraController {

    private final MatrizDoadoraService service;
    private final RacaService racaService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<MatrizDoadora> doadoras = service.getMatrizesDoadadoras();
        return ResponseEntity.ok(doadoras.stream().map(MatrizDoadoraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<MatrizDoadora> doadora = service.getMatrizDoadoraById(id);
        if (doadora.isEmpty()) {
            return new ResponseEntity<>("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(doadora.map(MatrizDoadoraDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody MatrizDoadoraDTO dto) {
        try {
            MatrizDoadora doadora = converter(dto);
            doadora = service.salvar(doadora);
            return new ResponseEntity<>(doadora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody MatrizDoadoraDTO dto) {
        if (service.getMatrizDoadoraById(id).isEmpty()) {
            return new ResponseEntity<>("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<MatrizDoadora> doadora = service.getMatrizDoadoraById(id);
        if (doadora.isEmpty()) {
            return new ResponseEntity<>("Matriz doadora não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(doadora.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
