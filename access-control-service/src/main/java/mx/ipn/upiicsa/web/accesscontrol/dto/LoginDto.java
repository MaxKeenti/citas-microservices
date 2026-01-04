package mx.ipn.upiicsa.web.accesscontrol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "Favor de proporcionar el nombre de usuario")
    @Email(message = "El nombre de usuario es incorrecto, favor de proporcionar un correo electrónico")
    public String username;

    @NotBlank(message = "Favor de proporcionar la contraseña")
    public String password;
}
