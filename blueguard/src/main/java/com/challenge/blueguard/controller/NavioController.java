package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.Navio;
import com.challenge.blueguard.service.NavioService;
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
@RequestMapping("/navios")
@Tag(name = "Navios", description = "API para gerenciar navios")
public class NavioController {

    @Autowired
    private NavioService navioService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todos os navios")
    public ResponseEntity<List<EntityModel<Navio>>> listar() {
        List<EntityModel<Navio>> navios = navioService.listarNavios().stream()
                .map(navio -> EntityModel.of(navio,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).consultarId(navio.getIdNavio()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).listar(), "navios")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(navios);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar um novo navio")
    public ResponseEntity<EntityModel<Navio>> criarNavio(@RequestBody Navio navio) {
        Navio novoNavio = navioService.salvarNavio(navio);
        EntityModel<Navio> resource = EntityModel.of(novoNavio,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(NavioController.class).consultarId(novoNavio.getIdNavio()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).listar(), "navios"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um navio existente")
    public ResponseEntity<EntityModel<Navio>> atualizarNavio(@PathVariable Long id, @RequestBody Navio navio){
        Navio navioAtualizado = navioService.atualizarNavio(navio, id);
        EntityModel<Navio> resource = EntityModel.of(navioAtualizado,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(NavioController.class).atualizarNavio(id, navio), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).listar(), "navios"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar um navio por ID")
    public ResponseEntity<EntityModel<Navio>> consultarId(@PathVariable Long id) {
        Navio navio = navioService.buscarNavioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Navio Não Encontrado"));
        EntityModel<Navio> resource = EntityModel.of(navio,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).consultarId(navio.getIdNavio()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(NavioController.class).listar(), "brinquedos"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um navio por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        navioService.removerNavio(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para um navio por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean navioExistente = navioService.buscarNavioPorId(id).isPresent();
        if (navioExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um navio")
    public ResponseEntity<EntityModel<Navio>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Navio navioAtualizado = navioService.atualizarParcialmente(id, updates);

        EntityModel<Navio> resource = EntityModel.of(navioAtualizado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NavioController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NavioController.class).listar()).withRel("navio"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NavioController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de um navio por ID")
    public ResponseEntity<?> consultarNavioHead(@PathVariable Long id) {
        Optional<Navio> navio = navioService.buscarNavioPorId(id);
        if (!navio.isPresent()) {
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