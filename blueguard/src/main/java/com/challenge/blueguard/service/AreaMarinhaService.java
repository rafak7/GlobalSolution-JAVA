package com.challenge.blueguard.service;

import com.challenge.blueguard.dto.AreaMarinhaDTO;
import com.challenge.blueguard.entity.AreaMarinha;
import com.challenge.blueguard.repository.AreaMarinhaRepository;
import com.challenge.blueguard.util.AreaMarinhaConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaMarinhaService {

    @Autowired
    private AreaMarinhaRepository areaMarinhaRepository;

    public List<AreaMarinhaDTO> listarAreaMarinha() {
        return areaMarinhaRepository.findAll()
                .stream()
                .map(AreaMarinhaConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AreaMarinhaDTO> buscarAreaMarinhaPorId(Long id) {
        return areaMarinhaRepository.findById(id)
                .map(AreaMarinhaConverter::toDTO);
    }

    public AreaMarinhaDTO salvarAreaMarinha(AreaMarinhaDTO areaMarinhaDTO) {
        AreaMarinha areaMarinha = AreaMarinhaConverter.toEntity(areaMarinhaDTO);
        AreaMarinha savedAreaMarinha = areaMarinhaRepository.save(areaMarinha);
        return AreaMarinhaConverter.toDTO(savedAreaMarinha);
    }

    public void removerAreaMarinha(Long id) {
        areaMarinhaRepository.deleteById(id);
    }

    public AreaMarinhaDTO atualizarAreaMarinha(AreaMarinhaDTO areaMarinhaDTO, Long id) {
        return areaMarinhaRepository.findById(id)
                .map(areaMarinhaExistente -> {
                    areaMarinhaExistente.setCoordenadas(areaMarinhaDTO.getCoordenadas());
                    areaMarinhaExistente.setNome(areaMarinhaDTO.getNome());
                    AreaMarinha updatedAreaMarinha = areaMarinhaRepository.save(areaMarinhaExistente);
                    return AreaMarinhaConverter.toDTO(updatedAreaMarinha);
                }).orElseThrow(() -> new IllegalStateException("Area com ID " + id + " nao existe"));
    }

    public AreaMarinhaDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        return areaMarinhaRepository.findById(id)
                .map(areaMarinhaExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(AreaMarinha.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, areaMarinhaExistente, value);
                        }
                    });
                    AreaMarinha updatedAreaMarinha = areaMarinhaRepository.save(areaMarinhaExistente);
                    return AreaMarinhaConverter.toDTO(updatedAreaMarinha);
                }).orElseThrow(() -> new IllegalArgumentException("Area com ID " + id + " nao existe."));
    }
}
