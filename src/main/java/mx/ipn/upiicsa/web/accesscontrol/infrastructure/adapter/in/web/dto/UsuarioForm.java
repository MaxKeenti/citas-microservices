package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioForm {
    @NotNull(message = "Seleccione una persona")
    private Integer idPersona;

    @NotNull(message = "Seleccione un rol")
    private Integer idRol;

    @NotBlank(message = "Favor de proporcionar el login (correo)")
    @Email(message = "El login debe ser un correo electrónico válido")
    private String login;

    @NotBlank(message = "Favor de proporcionar la contraseña")
    private String password;

    private Boolean activo = true;
}
