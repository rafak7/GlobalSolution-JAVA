package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.QualidadeAgua;
import com.challenge.blueguard.service.QualidadeAguaService;
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
@RequestMapping("/qualidade")
@Tag(name = "Qualidade da Água", description = "API para gerenciar a qualidade da água")
public class QualidadeAguaController {

    @Autowired
    private QualidadeAguaService qualidadeAguaService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas as qualidades da água")
    public ResponseEntity<List<EntityModel<QualidadeAgua>>> listar() {
        List<EntityModel<QualidadeAgua>> qualidade = qualidadeAguaService.listarQualidade().stream()
                .map(qualidadeAgua -> EntityModel.of(qualidadeAgua,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).consultarId(qualidadeAgua.getIdQualidade()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).listar(), "qualidade")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(qualidade);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova qualidade da água")
    public ResponseEntity<EntityModel<QualidadeAgua>> criaQualidade(@RequestBody QualidadeAgua qualidadeAgua) {
        QualidadeAgua novaQualidade = qualidadeAguaService.salvarQualidade(qualidadeAgua);
        EntityModel<QualidadeAgua> resource = EntityModel.of(novaQualidade,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).consultarId(novaQualidade.getIdQualidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).listar(), "qualidade"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma qualidade da água existente")
    public ResponseEntity<EntityModel<QualidadeAgua>> atualizarQualidade(@PathVariable Long id, @RequestBody QualidadeAgua qualidadeAgua){
        QualidadeAgua qualidadeAtualizada = qualidadeAguaService.atualizarQualidade(qualidadeAgua, id);
        EntityModel<QualidadeAgua> resource = EntityModel.of(qualidadeAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).atualizarQualidade(id, qualidadeAgua), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).listar(), "qualidade"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma qualidade da água por ID")
    public ResponseEntity<EntityModel<QualidadeAgua>> consultarId(@PathVariable Long id) {
        QualidadeAgua qualidadeAgua = qualidadeAguaService.buscarQualidadeAguaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Qualidade Não Encontrada"));
        EntityModel<QualidadeAgua> resource = EntityModel.of(qualidadeAgua,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).consultarId(qualidadeAgua.getIdQualidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).listar(), "qualidade"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma qualidade da água por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        qualidadeAguaService.removerQualidade(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma qualidade da água por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean qualidadeExistente = qualidadeAguaService.buscarQualidadeAguaPorId(id).isPresent();
        if (qualidadeExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente uma qualidade da água")
    public ResponseEntity<EntityModel<QualidadeAgua>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        QualidadeAgua qualidadeAtualizada = qualidadeAguaService.atualizarParcialmente(id, updates);

        EntityModel<QualidadeAgua> resource = EntityModel.of(qualidadeAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).listar()).withRel("qualidade"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QualidadeAguaController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma qualidade da água por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<QualidadeAgua> qualidadeAgua = qualidadeAguaService.buscarQualidadeAguaPorId(id);
        if (!qualidadeAgua.isPresent()) {
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