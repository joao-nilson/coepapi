package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.MachoReprodutorDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.entity.MachoReprodutor;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.ClienteService;
import com.example.sgpoepapi.service.MachoReprodutorService;
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
@RequestMapping("/api/v1/machosreprodutores")
@RequiredArgsConstructor
@CrossOrigin
public class MachoReprodutorController {

    private final MachoReprodutorService service;
    private final RacaService racaService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<MachoReprodutor> machos = service.getMachosReprodutores();
        return ResponseEntity.ok(machos.stream().map(MachoReprodutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<MachoReprodutor> macho = service.getMachoReprodutorById(id);
        if (macho.isEmpty()) {
            return new ResponseEntity<>("Macho reprodutor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(macho.map(MachoReprodutorDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody MachoReprodutorDTO dto) {
        try {
            MachoReprodutor macho = converter(dto);
            macho = service.salvar(macho);
            return new ResponseEntity<>(macho, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody MachoReprodutorDTO dto) {
        if (service.getMachoReprodutorById(id).isEmpty()) {
            return new ResponseEntity<>("Macho reprodutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            MachoReprodutor macho = converter(dto);
            macho.setId(id);
            service.salvar(macho);
            return ResponseEntity.ok(macho);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<MachoReprodutor> macho = service.getMachoReprodutorById(id);
        if (macho.isEmpty()) {
            return new ResponseEntity<>("Macho reprodutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(macho.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MachoReprodutor converter(MachoReprodutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        MachoReprodutor macho = modelMapper.map(dto, MachoReprodutor.class);
        if (dto.getRacaId() != null) {
            Optional<Raca> raca = racaService.getRacaById(dto.getRacaId());
            macho.setRaca(raca.orElse(null));
        }
        if (dto.getProprietarioId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioId());
            macho.setProprietario(cliente.orElse(null));
        }
        return macho;
    }
}
