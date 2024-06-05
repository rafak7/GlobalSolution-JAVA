package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.Navio;
import com.challenge.blueguard.repository.NavioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NavioService {

    @Autowired
    private NavioRepository navioRepository;

    public List<Navio> listarNavios(){
        return navioRepository.findAll();
    }

    public Optional<Navio> buscarNavioPorId(Long Id){
        return navioRepository.findById(Id);
    }

    public Navio salvarNavio(Navio navio){
        return navioRepository.save(navio);
    }

    public void removerNavio(Long Id){
        navioRepository.deleteById(Id);
    }

    public Navio atualizarNavio (Navio navio, Long id) {
        return navioRepository.findById(id)
                .map(navioExistente -> {
                    navioExistente.setNomeNavio(navio.getNomeNavio());
                    navioExistente.setImoNavio(navio.getImoNavio());
                    navioExistente.setMmsiNavio(navio.getImoNavio());
                    navioExistente.setTipoNavio(navio.getTipoNavio());
                    return navioRepository.save(navioExistente);
                }).orElseThrow(() -> new IllegalStateException("Navio com ID " + id + " nao existe"));
    }

    public Navio atualizarParcialmente(Long id, Map<String, Object> updates){
        return navioRepository.findById(id)
                .map(navioExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Navio.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, navioExistente, value );
                        }
                    });
                    return navioRepository.save(navioExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Navio com ID " + id + " nao existe."));
    }

}
