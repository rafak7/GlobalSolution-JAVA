package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.AreaMarinha;
import com.challenge.blueguard.service.AreaMarinhaService;
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
@RequestMapping("/area")
@Tag(name = "Área Marinha", description = "API para gerenciar áreas marinhas")
public class AreaMarinhaController {

    @Autowired
    private AreaMarinhaService areaMarinhaService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas as áreas marinhas")
    public ResponseEntity<List<EntityModel<AreaMarinha>>> listar() {
        List<EntityModel<AreaMarinha>> area = areaMarinhaService.listarAreaMarinha().stream()
                .map(areaMarinha -> EntityModel.of(areaMarinha,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).consultarId(areaMarinha.getIdAreaMarinha()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).listar(), "area")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(area);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova área marinha")
    public ResponseEntity<EntityModel<AreaMarinha>> criarArea(@RequestBody AreaMarinha areaMarinha) {
        AreaMarinha novaArea = areaMarinhaService.salvarAreaMarinha(areaMarinha);
        EntityModel<AreaMarinha> resource = EntityModel.of(novaArea,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).consultarId(novaArea.getIdAreaMarinha()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).listar(), "area"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma área marinha existente")
    public ResponseEntity<EntityModel<AreaMarinha>> atualizarArea(@PathVariable Long id, @RequestBody AreaMarinha areaMarinha){
        AreaMarinha areaAtualizada = areaMarinhaService.atualizarAreaMarinha(areaMarinha, id);
        EntityModel<AreaMarinha> resource = EntityModel.of(areaAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).atualizarArea(id, areaMarinha), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).listar(), "localizacao"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma área marinha por ID")
    public ResponseEntity<EntityModel<AreaMarinha>> consultarId(@PathVariable Long id) {
        AreaMarinha areaMarinha = areaMarinhaService.buscarAreaMarinhaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Área Não Encontrada"));
        EntityModel<AreaMarinha> resource = EntityModel.of(areaMarinha,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).consultarId(areaMarinha.getIdAreaMarinha()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).listar(), "area"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma área marinha por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        areaMarinhaService.removerAreaMarinha(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma área marinha por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean areaExistente = areaMarinhaService.buscarAreaMarinhaPorId(id).isPresent();
        if (areaExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente uma área marinha")
    public ResponseEntity<EntityModel<AreaMarinha>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        AreaMarinha areaAtualizada = areaMarinhaService.atualizarParcialmente(id, updates);

        EntityModel<AreaMarinha> resource = EntityModel.of(areaAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).listar()).withRel("area"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AreaMarinhaController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma área marinha por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<AreaMarinha> areaMarinha = areaMarinhaService.buscarAreaMarinhaPorId(id);
        if (!areaMarinha.isPresent()) {
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