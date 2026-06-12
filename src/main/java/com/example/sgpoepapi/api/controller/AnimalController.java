package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.AnimalDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Animal;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.entity.Raca;
import com.example.sgpoepapi.service.AnimalService;
import com.example.sgpoepapi.service.ClienteService;
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
@RequestMapping("/api/v1/animais")
@RequiredArgsConstructor
@CrossOrigin
public class AnimalController {

    private final AnimalService service;
    private final RacaService racaService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Animal> animais = service.getAnimais();
        return ResponseEntity.ok(animais.stream().map(AnimalDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Animal> animal = service.getAnimalById(id);
        if (animal.isEmpty()) {
            return new ResponseEntity<>("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(animal.map(AnimalDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody AnimalDTO dto) {
        try {
            Animal animal = converter(dto);
            animal = service.salvar(animal);
            return new ResponseEntity<>(animal, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody AnimalDTO dto) {
        if (service.getAnimalById(id).isEmpty()) {
            return new ResponseEntity<>("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Animal animal = converter(dto);
            animal.setId(id);
            service.salvar(animal);
            return ResponseEntity.ok(animal);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Animal> animal = service.getAnimalById(id);
        if (animal.isEmpty()) {
            return new ResponseEntity<>("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(animal.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Animal converter(AnimalDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Animal animal = modelMapper.map(dto, Animal.class);
        if (dto.getRacaId() != null) {
            Optional<Raca> raca = racaService.getRacaById(dto.getRacaId());
            animal.setRaca(raca.orElse(null));
        }
        if (dto.getProprietarioId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioId());
            animal.setProprietario(cliente.orElse(null));
        }
        return animal;
    }
}
