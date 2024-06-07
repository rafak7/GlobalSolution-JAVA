package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.AreaMarinha;
import com.challenge.blueguard.entity.Marcacoes;
import com.challenge.blueguard.repository.MarcacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarcacoesService {

    @Autowired
    private MarcacoesRepository marcacoesRepository;

    public List<Marcacoes> listarMarcacoes() {
        return marcacoesRepository.findAll();
    }

    public Optional<Marcacoes> buscarMarcacoesPorId(Long Id) {
        return marcacoesRepository.findById(Id);
    }

    public Marcacoes salvarMarcacoes (Marcacoes marcacoes) {
        return marcacoesRepository.save(marcacoes);
    }

    public void removerMarcacoes(Long Id){
        marcacoesRepository.deleteById(Id);
    }

    public Marcacoes atualizarMarcacoes (Marcacoes marcacoes, Long id) {
        return marcacoesRepository.findById(id)
                .map(marcacaoExistente -> {
                    marcacaoExistente.setPh(marcacoes.getPh());
                    marcacaoExistente.setPotavel(marcacoes.getPotavel());
                    marcacaoExistente.setLocal(marcacoes.getLocal());
                    marcacaoExistente.setObservacoes(marcacoes.getObservacoes());
                    marcacaoExistente.setTemperatura(marcacoes.getTemperatura());
                    marcacaoExistente.setNivelSujeira(marcacoes.getNivelSujeira());
                    return marcacoesRepository.save(marcacaoExistente);
                }).orElseThrow(() -> new IllegalStateException("Area com ID " + id + " nao existe"));
    }

    public Marcacoes atualizarParcialmente(Long id, Map<String, Object> updates){
        return marcacoesRepository.findById(id)
                .map(marcacaoExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Marcacoes.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, marcacaoExistente, value );
                        }
                    });
                    return marcacoesRepository.save(marcacaoExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Marcacao com ID " + id + " nao existe."));
    }

}
