package com.challenge.blueguard.service;


import com.challenge.blueguard.entity.Comunidade;
import com.challenge.blueguard.entity.Marcacoes;
import com.challenge.blueguard.repository.ComunidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComunidadeService {

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    public List<Comunidade> listarComunidade() {
        return comunidadeRepository.findAll();
    }

    public Optional<Comunidade> buscarComunidadePorId(Long Id) {
        return comunidadeRepository.findById(Id);
    }

    public Comunidade salvarComunidade (Comunidade comunidade) {
        return comunidadeRepository.save(comunidade);
    }

    public void removerComunidade(Long Id){
        comunidadeRepository.deleteById(Id);
    }

    public Comunidade atualizarComunidade (Comunidade comunidade, Long id) {
        return comunidadeRepository.findById(id)
                .map(comunidadeExistente -> {
                    comunidadeExistente.setDescricao(comunidade.getDescricao());
                    comunidadeExistente.setDataHora(comunidade.getDataHora());
                    comunidadeExistente.setLocal(comunidade.getLocal());
                    comunidadeExistente.setNomeUsuario(comunidade.getNomeUsuario());
                    return comunidadeRepository.save(comunidadeExistente);
                }).orElseThrow(() -> new IllegalStateException("Comunidade com ID " + id + " nao existe"));
    }

    public Comunidade atualizarParcialmente(Long id, Map<String, Object> updates){
        return comunidadeRepository.findById(id)
                .map(comunidadeExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Comunidade.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, comunidadeExistente, value );
                        }
                    });
                    return comunidadeRepository.save(comunidadeExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Comunidade com ID " + id + " nao existe."));
    }

}
