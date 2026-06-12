package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.OocitoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.MatrizDoadora;
import com.example.sgpoepapi.model.entity.Oocito;
import com.example.sgpoepapi.model.entity.OocitoClassificacao;
import com.example.sgpoepapi.service.MatrizDoadoraService;
import com.example.sgpoepapi.service.OocitoClassificacaoService;
import com.example.sgpoepapi.service.OocitoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/oocitos")
@RequiredArgsConstructor
@CrossOrigin
public class OocitoController {

    private final OocitoService service;
    private final MatrizDoadoraService matrizDoadoraService;
    private final OocitoClassificacaoService oocitoClassificacaoService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Oocito> oocitos = service.getOocitos();
        return ResponseEntity.ok(oocitos.stream().map(OocitoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Oocito> oocito = service.getOocitoById(id);
        if (oocito.isEmpty()) {
            return new ResponseEntity<>("Oócito não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(oocito.map(OocitoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody OocitoDTO dto) {
        try {
            Oocito oocito = converter(dto);
            oocito = service.salvar(oocito);
            return new ResponseEntity<>(oocito, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody OocitoDTO dto) {
        if (service.getOocitoById(id).isEmpty()) {
            return new ResponseEntity<>("Oócito não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Oocito oocito = converter(dto);
            oocito.setId(id);
            service.salvar(oocito);
            return ResponseEntity.ok(oocito);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Oocito> oocito = service.getOocitoById(id);
        if (oocito.isEmpty()) {
            return new ResponseEntity<>("Oócito não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(oocito.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Oocito converter(OocitoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Oocito oocito = modelMapper.map(dto, Oocito.class);
        if (dto.getMaeId() != null) {
            Optional<MatrizDoadora> mae = matrizDoadoraService.getMatrizDoadoraById(dto.getMaeId());
            oocito.setMae(mae.orElse(null));
        }
        if (dto.getClassificacaoId() != null) {
            Optional<OocitoClassificacao> classificacao = oocitoClassificacaoService.getOocitoClassificacaoById(dto.getClassificacaoId());
            oocito.setClassificacao(classificacao.orElse(null));
        }
        return oocito;
    }
}
