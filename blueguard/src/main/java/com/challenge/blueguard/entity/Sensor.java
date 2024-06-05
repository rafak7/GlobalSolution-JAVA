package com.challenge.blueguard.entity;
import lombok.*;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_SENSOR", allocationSize = 1)
@Table (name = "sensor")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Sensor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "invs")
    @Column(name = "ID")
    private Long idSensor;

    @Column(name = "LOCALIZACAO", length = 255, nullable = false)
    private String localizacaoSensor;

    @Column(name = "TIPO", length = 255, nullable = false)
    private String tipoSensor;

    @Column(name = "STATUS", length = 255, nullable = false)
    private String statusSensor;

}
