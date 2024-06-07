package com.challenge.blueguard.util;

import com.challenge.blueguard.dto.QualidadeAguaDTO;
import com.challenge.blueguard.entity.QualidadeAgua;

public class QualidadeAguaConverter {

    public static QualidadeAguaDTO toDTO(QualidadeAgua qualidadeAgua) {
        return new QualidadeAguaDTO(
                qualidadeAgua.getIdQualidade(),
                qualidadeAgua.getTimestamp(),
                qualidadeAgua.getTemperatura(),
                qualidadeAgua.getPh(),
                qualidadeAgua.getSalinidade(),
                qualidadeAgua.getOxigenio()
        );
    }

    public static QualidadeAgua toEntity(QualidadeAguaDTO qualidadeAguaDTO) {
        return new QualidadeAgua(
                qualidadeAguaDTO.getIdQualidade(),
                qualidadeAguaDTO.getTimestamp(),
                qualidadeAguaDTO.getTemperatura(),
                qualidadeAguaDTO.getPh(),
                qualidadeAguaDTO.getSalinidade(),
                qualidadeAguaDTO.getOxigenio()
        );
    }
}
