package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MatrizReceptoraDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.entity.MatrizReceptora;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.ClienteService;
import com.example.sgpoepapi.service.MatrizReceptoraService;
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
@RequestMapping("/api/v1/matrizesreceptoras")
@RequiredArgsConstructor
@CrossOrigin
public class MatrizReceptoraController {

    private final MatrizReceptoraService service;
    private final RacaService racaService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<MatrizReceptora> receptoras = service.getMatrizesReceptoras();
        return ResponseEntity.ok(receptoras.stream().map(MatrizReceptoraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<MatrizReceptora> receptora = service.getMatrizReceptoraById(id);
        if (receptora.isEmpty()) {
            return new ResponseEntity<>("Matriz receptora não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(receptora.map(MatrizReceptoraDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody MatrizReceptoraDTO dto) {
        try {
            MatrizReceptora receptora = converter(dto);
            receptora = service.salvar(receptora);
            return new ResponseEntity<>(receptora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody MatrizReceptoraDTO dto) {
        if (service.getMatrizReceptoraById(id).isEmpty()) {
            return new ResponseEntity<>("Matriz receptora não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            MatrizReceptora receptora = converter(dto);
            receptora.setId(id);
            service.salvar(receptora);
            return ResponseEntity.ok(receptora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<MatrizReceptora> receptora = service.getMatrizReceptoraById(id);
        if (receptora.isEmpty()) {
            return new ResponseEntity<>("Matriz receptora não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(receptora.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MatrizReceptora converter(MatrizReceptoraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        MatrizReceptora receptora = modelMapper.map(dto, MatrizReceptora.class);
        if (dto.getRacaId() != null) {
            Optional<Raca> raca = racaService.getRacaById(dto.getRacaId());
            receptora.setRaca(raca.orElse(null));
        }
        if (dto.getProprietarioId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioId());
            receptora.setProprietario(cliente.orElse(null));
        }
        return receptora;
    }
}
