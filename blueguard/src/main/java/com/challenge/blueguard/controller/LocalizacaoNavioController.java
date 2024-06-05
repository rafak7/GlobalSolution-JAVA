package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.LocalizacaoNavio;
import com.challenge.blueguard.service.LocalizacaoNavioService;
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
@RequestMapping("/localizacao")
@Tag(name = "Localização de Navio", description = "API para gerenciar a localização de navios")
public class LocalizacaoNavioController {

    @Autowired
    private LocalizacaoNavioService localizacaoNavioService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas as localizações de navios")
    public ResponseEntity<List<EntityModel<LocalizacaoNavio>>> listar() {
        List<EntityModel<LocalizacaoNavio>> localizacao = localizacaoNavioService.listarLocalizacaoNavio().stream()
                .map(localizacaoNavio -> EntityModel.of(localizacaoNavio,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).consultarId(localizacaoNavio.getIdLocalizacao()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).listar(), "localizacao")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(localizacao);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova localização de navio")
    public ResponseEntity<EntityModel<LocalizacaoNavio>> criarLocalizacao(@RequestBody LocalizacaoNavio localizacaoNavio) {
        LocalizacaoNavio novaLocalizacao = localizacaoNavioService.salvarLocalizacao(localizacaoNavio);
        EntityModel<LocalizacaoNavio> resource = EntityModel.of(novaLocalizacao,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).consultarId(novaLocalizacao.getIdLocalizacao()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).listar(), "localizacao"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma localização de navio existente")
    public ResponseEntity<EntityModel<LocalizacaoNavio>> atualizarLocalizacao(@PathVariable Long id, @RequestBody LocalizacaoNavio localizacaoNavio){
        LocalizacaoNavio localizacaoAtualizada = localizacaoNavioService.atualizarLocalizacao(localizacaoNavio, id);
        EntityModel<LocalizacaoNavio> resource = EntityModel.of(localizacaoAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).atualizarLocalizacao(id, localizacaoNavio), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).listar(), "localizacao"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma localização de navio por ID")
    public ResponseEntity<EntityModel<LocalizacaoNavio>> consultarId(@PathVariable Long id) {
        LocalizacaoNavio localizacaoNavio = localizacaoNavioService.buscarLocalizacaoNavioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Localização Não Encontrada"));
        EntityModel<LocalizacaoNavio> resource = EntityModel.of(localizacaoNavio,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).consultarId(localizacaoNavio.getIdLocalizacao()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).listar(), "sensor"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma localização de navio por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        localizacaoNavioService.removerLocalizacao(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma localização de navio por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean localizacaoExistente = localizacaoNavioService.buscarLocalizacaoNavioPorId(id).isPresent();
        if (localizacaoExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente uma localização de navio")
    public ResponseEntity<EntityModel<LocalizacaoNavio>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        LocalizacaoNavio localizacaoAtualizada = localizacaoNavioService.atualizarParcialmente(id, updates);

        EntityModel<LocalizacaoNavio> resource = EntityModel.of(localizacaoAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).listar()).withRel("localizacao"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LocalizacaoNavioController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma localização de navio por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<LocalizacaoNavio> localizacaoNavio = localizacaoNavioService.buscarLocalizacaoNavioPorId(id);
        if (!localizacaoNavio.isPresent()) {
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