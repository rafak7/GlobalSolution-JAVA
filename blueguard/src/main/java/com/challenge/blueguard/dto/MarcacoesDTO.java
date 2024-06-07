package com.challenge.blueguard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarcacoesDTO {
    private Long idMarcacoes;
    private String local;
    private String nivelSujeira;
    private BigDecimal ph;
    private BigDecimal temperatura;
    private String potavel;
    private String observacoes;
}
