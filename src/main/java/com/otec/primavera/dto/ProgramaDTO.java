package com.otec.primavera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.otec.primavera.model.Programa;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionHoras;
    private String tecnologia;
    private Programa.Modalidad modalidad;
    private Programa.Nivel nivel;
    private Programa.Estado estado;
}