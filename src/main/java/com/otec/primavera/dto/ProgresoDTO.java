package com.otec.primavera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.otec.primavera.model.Hito;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoDTO {

    private String nombrePrograma;
    private String tecnologia;
    private Integer duracionHoras;
    private LocalDate fechaInicio;
    private String estadoMatricula;
    private Integer porcentajeAvance;
    private List<HitoDTO> hitos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HitoDTO {
        private String nombreModulo;
        private Hito.Tipo tipo;
        private Double nota;
        private LocalDate fecha;
        private String observacion;
    }
}