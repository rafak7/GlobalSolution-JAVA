package com.challenge.blueguard.util;

import com.challenge.blueguard.dto.AreaMarinhaDTO;
import com.challenge.blueguard.entity.AreaMarinha;

public class AreaMarinhaConverter {

    public static AreaMarinhaDTO toDTO(AreaMarinha areaMarinha) {
        return new AreaMarinhaDTO(
                areaMarinha.getIdAreaMarinha(),
                areaMarinha.getNome(),
                areaMarinha.getCoordenadas()
        );
    }

    public static AreaMarinha toEntity(AreaMarinhaDTO areaMarinhaDTO) {
        return new AreaMarinha(
                areaMarinhaDTO.getIdAreaMarinha(),
                areaMarinhaDTO.getNome(),
                areaMarinhaDTO.getCoordenadas()
        );
    }
}
