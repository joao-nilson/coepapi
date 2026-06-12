package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.FivDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Embriao;
import com.example.sgpoepapi.model.entity.FIV;
import com.example.sgpoepapi.model.entity.MachoReprodutor;
import com.example.sgpoepapi.model.entity.MatrizDoadora;
import com.example.sgpoepapi.service.EmbriaoService;
import com.example.sgpoepapi.service.FIVService;
import com.example.sgpoepapi.service.MachoReprodutorService;
import com.example.sgpoepapi.service.MatrizDoadoraService;
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
@RequestMapping("/api/v1/fivs")
@RequiredArgsConstructor
@CrossOrigin
public class FIVController {

    private final FIVService service;
    private final MatrizDoadoraService matrizDoadoraService;
    private final MachoReprodutorService machoReprodutorService;
    private final EmbriaoService embriaoService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<FIV> fivs = service.getFIVs();
        return ResponseEntity.ok(fivs.stream().map(FivDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<FIV> fiv = service.getFIVById(id);
        if (fiv.isEmpty()) {
            return new ResponseEntity<>("FIV não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fiv.map(FivDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody FivDTO dto) {
        try {
            FIV fiv = converter(dto);
            fiv = service.salvar(fiv);
            return new ResponseEntity<>(fiv, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody FivDTO dto) {
        if (service.getFIVById(id).isEmpty()) {
            return new ResponseEntity<>("FIV não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            FIV fiv = converter(dto);
            fiv.setId(id);
            service.salvar(fiv);
            return ResponseEntity.ok(fiv);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<FIV> fiv = service.getFIVById(id);
        if (fiv.isEmpty()) {
            return new ResponseEntity<>("FIV não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fiv.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FIV converter(FivDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FIV fiv = modelMapper.map(dto, FIV.class);
        if (dto.getMaeId() != null) {
            Optional<MatrizDoadora> mae = matrizDoadoraService.getMatrizDoadoraById(dto.getMaeId());
            fiv.setMae(mae.orElse(null));
        }
        if (dto.getPaiId() != null) {
            Optional<MachoReprodutor> pai = machoReprodutorService.getMachoReprodutorById(dto.getPaiId());
            fiv.setPai(pai.orElse(null));
        }
        if (dto.getEmbrioesIds() != null) {
            List<Embriao> embrioes = dto.getEmbrioesIds().stream()
                    .map(id -> embriaoService.getEmbriaoById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            fiv.setEmbrioes(embrioes);
        }
        return fiv;
    }
}
