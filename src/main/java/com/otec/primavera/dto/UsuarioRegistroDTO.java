package com.otec.primavera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.otec.primavera.model.Usuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Usuario.Rol rol;
}