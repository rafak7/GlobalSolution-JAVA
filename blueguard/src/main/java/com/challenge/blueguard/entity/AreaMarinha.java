package com.challenge.blueguard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Clob;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_AREAMARINHA", allocationSize = 1)
@Table (name = "TB_AREA_MARINHA")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AreaMarinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invs")
    @Column (name = "ID")
    private Long idAreaMarinha;

    @Column (name = "NOME", length = 255, nullable = false)
    private String nome;

    @Lob
    @Column (name = "COORDENADAS")
    private String coordenadas;

}
