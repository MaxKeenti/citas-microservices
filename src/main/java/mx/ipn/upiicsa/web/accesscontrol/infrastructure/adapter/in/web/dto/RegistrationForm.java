package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationForm {
    // Persona fields
    @NotNull(message = "Seleccione un género")
    private Integer idGenero;
    @NotBlank(message = "Favor de proporcionar el nombre")
    private String nombre;
    @NotBlank(message = "Favor de proporcionar el primer apellido")
    private String primerApellido;
    private String segundoApellido;
    @NotNull(message = "Favor de proporcionar la fecha de nacimiento")
    private java.time.LocalDate fechaNacimiento;

    // Usuario fields
    @NotBlank(message = "Favor de proporcionar el login (correo)")
    @Email(message = "El login debe ser un correo electrónico válido")
    private String login;
    @NotBlank(message = "Favor de proporcionar la contraseña")
    private String password;
    @NotBlank(message = "Favor de confirmar la contraseña")
    private String confirmPassword;
}
