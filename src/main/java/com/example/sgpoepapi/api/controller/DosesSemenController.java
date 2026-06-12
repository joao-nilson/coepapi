package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.DosesSemenDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DosesSemen;
import com.example.sgpoepapi.model.entity.MachoReprodutor;
import com.example.sgpoepapi.service.DosesSemenService;
import com.example.sgpoepapi.service.MachoReprodutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dosessemen")
@RequiredArgsConstructor
@CrossOrigin
public class DosesSemenController {

    private final DosesSemenService service;
    private final MachoReprodutorService machoReprodutorService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<DosesSemen> doses = service.getDosesSemen();
        return ResponseEntity.ok(doses.stream().map(DosesSemenDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<DosesSemen> doses = service.getDosesSemenById(id);
        if (doses.isEmpty()) {
            return new ResponseEntity<>("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(doses.map(DosesSemenDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody DosesSemenDTO dto) {
        try {
            DosesSemen doses = converter(dto);
            doses = service.salvar(doses);
            return new ResponseEntity<>(doses, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody DosesSemenDTO dto) {
        if (service.getDosesSemenById(id).isEmpty()) {
            return new ResponseEntity<>("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        try {
            DosesSemen doses = converter(dto);
            doses.setId(id);
            service.salvar(doses);
            return ResponseEntity.ok(doses);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<DosesSemen> doses = service.getDosesSemenById(id);
        if (doses.isEmpty()) {
            return new ResponseEntity<>("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(doses.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public DosesSemen converter(DosesSemenDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        DosesSemen doses = modelMapper.map(dto, DosesSemen.class);
        if (dto.getMachoReprodutorId() != null) {
            Optional<MachoReprodutor> macho = machoReprodutorService.getMachoReprodutorById(dto.getMachoReprodutorId());
            doses.setMachoReprodutor(macho.orElse(null));
        }
        return doses;
    }
}
