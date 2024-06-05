package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.Sensor;
import com.challenge.blueguard.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
@Tag(name = "Sensores", description = "API para gerenciar sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todos os sensores")
    public ResponseEntity<List<EntityModel<Sensor>>> listar() {
        List<EntityModel<Sensor>> sensores = sensorService.listarSensor().stream()
                .map(sensor -> EntityModel.of(sensor,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).consultarId(sensor.getIdSensor()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).listar(), "sensores")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensores);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar um novo sensor")
    public ResponseEntity<EntityModel<Sensor>> criarSensor(@RequestBody Sensor sensor) {
        Sensor novoSensor = sensorService.salvarSensor(sensor);
        EntityModel<Sensor> resource = EntityModel.of(novoSensor,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(SensorController.class).consultarId(novoSensor.getIdSensor()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).listar(), "sensores"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um sensor existente")
    public ResponseEntity<EntityModel<Sensor>> atualizarSensor(@PathVariable Long id, @RequestBody Sensor sensor){
        Sensor sensorAtualizado = sensorService.atualizarSensor(sensor, id);
        EntityModel<Sensor> resource = EntityModel.of(sensorAtualizado,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(SensorController.class).atualizarSensor(id, sensor), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).listar(), "sensores"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar um sensor por ID")
    public ResponseEntity<EntityModel<Sensor>> consultarId(@PathVariable Long id) {
        Sensor sensor = sensorService.buscarSensorPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor Não Encontrado"));
        EntityModel<Sensor> resource = EntityModel.of(sensor,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).consultarId(sensor.getIdSensor()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(SensorController.class).listar(), "sensores"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um sensor por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        sensorService.removerSensor(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para um sensor por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean sensorExistente = sensorService.buscarSensorPorId(id).isPresent();
        if (sensorExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um sensor")
    public ResponseEntity<EntityModel<Sensor>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Sensor sensorAtualizado = sensorService.atualizarParcialmente(id, updates);

        EntityModel<Sensor> resource = EntityModel.of(sensorAtualizado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class).listar()).withRel("sensores"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de um sensor por ID")
    public ResponseEntity<?> consultarSensorHead(@PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.buscarSensorPorId(id);
        if (!sensor.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    private Link createLinkWithMethod(String method, Object invocationValue, String rel) {
        return Link.of(WebMvcLinkBuilder.linkTo(invocationValue).toUri().toString(), rel)
                .withTitle(method + " method");
    }
}