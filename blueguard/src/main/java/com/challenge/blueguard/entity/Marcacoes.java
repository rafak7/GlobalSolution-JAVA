package com.challenge.blueguard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_MARCACOES", allocationSize = 1)
@Table (name = "MARCACOES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Marcacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invs")
    @Column (name = "ID", nullable = false)
    private Long idMarcacoes;

    @Column (name = "LOCAL", length = 255, nullable = false)
    private String local;

    @Column (name = "NIVEL_SUJEIRA", length = 50, nullable = false)
    private String nivelSujeira;

    @Column(name = "PH", nullable = false, precision = 3, scale = 1)
    private BigDecimal ph;

    @Column(name = "TEMPERATURA", nullable = false, precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(name = "POTAVEL", nullable = false)
    private String potavel;

    @Column(name = "OBSERVACOES", length = 255, nullable = false)
    private String observacoes;

}
