package com.otec.primavera.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programas")
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer duracionHoras;

    @Column(nullable = false)
    private String tecnologia;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL)
    private List<Modulo> modulos;

    public enum Modalidad {
        ONLINE, PRESENCIAL, HIBRIDO
    }

    public enum Nivel {
        BASICO, INTERMEDIO, AVANZADO
    }

    public enum Estado {
        ACTIVO, INACTIVO
    }
}
