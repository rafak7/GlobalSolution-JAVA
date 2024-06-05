package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.Sensor;
import com.challenge.blueguard.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> listarSensor() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> buscarSensorPorId(Long Id) {
        return sensorRepository.findById(Id);
    }

    public Sensor salvarSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public void removerSensor(Long Id){
        sensorRepository.deleteById(Id);
    }

    public Sensor atualizarSensor (Sensor sensor, Long id) {
        return sensorRepository.findById(id)
                .map(sensorExistente -> {
                    sensorExistente.setLocalizacaoSensor(sensor.getLocalizacaoSensor());
                    sensorExistente.setStatusSensor(sensor.getStatusSensor());
                    sensorExistente.setTipoSensor(sensor.getTipoSensor());
                    return sensorRepository.save(sensorExistente);
                }).orElseThrow(() -> new IllegalStateException("Sensor com ID " + id + " nao existe"));
    }

    public Sensor atualizarParcialmente(Long id, Map<String, Object> updates){
        return sensorRepository.findById(id)
                .map(sensorExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Sensor.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, sensorExistente, value );
                        }
                    });
                    return sensorRepository.save(sensorExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Navio com ID " + id + " nao existe."));
    }

}
