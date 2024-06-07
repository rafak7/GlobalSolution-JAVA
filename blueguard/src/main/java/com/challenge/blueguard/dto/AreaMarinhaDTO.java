package com.challenge.blueguard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaMarinhaDTO {

    private Long idAreaMarinha;
    private String nome;
    private String coordenadas;
}
