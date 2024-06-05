package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.LocalizacaoNavio;
import com.challenge.blueguard.entity.Navio;
import com.challenge.blueguard.repository.LocalizacaoNavioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LocalizacaoNavioService {

    @Autowired
    private LocalizacaoNavioRepository localizacaoNavioRepository;

    public List<LocalizacaoNavio> listarLocalizacaoNavio() {
        return localizacaoNavioRepository.findAll();
    }

    public Optional<LocalizacaoNavio> buscarLocalizacaoNavioPorId(Long Id) {
        return localizacaoNavioRepository.findById(Id);
    }

    public LocalizacaoNavio salvarLocalizacao (LocalizacaoNavio localizacaoNavio) {
        return localizacaoNavioRepository.save(localizacaoNavio);
    }

    public void removerLocalizacao(Long Id){
        localizacaoNavioRepository.deleteById(Id);
    }

    public LocalizacaoNavio atualizarLocalizacao (LocalizacaoNavio localizacaoNavio, Long id) {
        return localizacaoNavioRepository.findById(id)
                .map(localizacaoExistente -> {
                    localizacaoExistente.setLatitude(localizacaoNavio.getLatitude());
                    localizacaoExistente.setLongitude(localizacaoNavio.getLongitude());
                    localizacaoExistente.setVelocidade(localizacaoNavio.getVelocidade());
                    localizacaoExistente.setTimestamp(localizacaoNavio.getTimestamp());
                    return localizacaoNavioRepository.save(localizacaoExistente);
                }).orElseThrow(() -> new IllegalStateException("Localizacao com ID " + id + " nao existe"));
    }

    public LocalizacaoNavio atualizarParcialmente(Long id, Map<String, Object> updates){
        return localizacaoNavioRepository.findById(id)
                .map(localizacaoExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(LocalizacaoNavio.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, localizacaoExistente, value );
                        }
                    });
                    return localizacaoNavioRepository.save(localizacaoExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Localizacao com ID " + id + " nao existe."));
    }

}
