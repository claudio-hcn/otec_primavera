package com.otec.primavera.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hitos")
public class Hito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Column
    private Double nota;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 500)
    private String observacion;

    public enum Tipo {
        MODULO_COMPLETADO, EVALUACION, ASISTENCIA
    }

    @Column
    private Boolean aprobado;
}