package com.challenge.blueguard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_LOCALIZACAO_NAVIO", allocationSize = 1)
@Table(name = "LOCALIZACAO_NAVIO")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalizacaoNavio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invs")
    @Column(name = "ID", nullable = false)
    private Long idLocalizacao;

    @Column(name = "ID_NAVIO", nullable = false)
    private Long idNavio;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "VELOCIDADE", nullable = false)
    private Double velocidade;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;
}