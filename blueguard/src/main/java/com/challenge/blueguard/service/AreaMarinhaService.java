package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.AreaMarinha;
import com.challenge.blueguard.entity.LocalizacaoNavio;
import com.challenge.blueguard.repository.AreaMarinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AreaMarinhaService {

    @Autowired
    private AreaMarinhaRepository areaMarinhaRepository;

    public List<AreaMarinha> listarAreaMarinha() {
        return areaMarinhaRepository.findAll();
    }

    public Optional<AreaMarinha> buscarAreaMarinhaPorId(Long Id) {
        return areaMarinhaRepository.findById(Id);
    }

    public AreaMarinha salvarAreaMarinha (AreaMarinha areaMarinha) {
        return areaMarinhaRepository.save(areaMarinha);
    }

    public void removerAreaMarinha(Long Id){
        areaMarinhaRepository.deleteById(Id);
    }

    public AreaMarinha atualizarAreaMarinha (AreaMarinha areaMarinha, Long id) {
        return areaMarinhaRepository.findById(id)
                .map(areaMarinhaExistente -> {
                    areaMarinhaExistente.setCoordenadas(areaMarinha.getCoordenadas());
                    areaMarinhaExistente.setNome(areaMarinha.getNome());
                    return areaMarinhaRepository.save(areaMarinhaExistente);
                }).orElseThrow(() -> new IllegalStateException("Area com ID " + id + " nao existe"));
    }

    public AreaMarinha atualizarParcialmente(Long id, Map<String, Object> updates){
        return areaMarinhaRepository.findById(id)
                .map(areaMarinhaExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(AreaMarinha.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, areaMarinhaExistente, value );
                        }
                    });
                    return areaMarinhaRepository.save(areaMarinhaExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Area com ID " + id + " nao existe."));
    }

}
