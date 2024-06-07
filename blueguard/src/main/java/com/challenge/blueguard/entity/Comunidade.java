package com.challenge.blueguard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_COMUNIDADE", allocationSize = 1)
@Table(name = "COMUNIDADE")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Comunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invs")
    @Column (name = "ID", nullable = false)
    private Long idComunidade;

    @Column(name = "NOME_USUARIO", length = 255, nullable = false)
    private String nomeUsuario;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "LOCAL", length = 255, nullable = false)
    private String local;

    @Column(name = "DESCRICAO", length = 255, nullable = false)
    private String descricao;
}
