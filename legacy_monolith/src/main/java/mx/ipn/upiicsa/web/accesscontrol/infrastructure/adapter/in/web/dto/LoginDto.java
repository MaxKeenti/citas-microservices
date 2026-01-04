package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "Favor de proporcionar el nombre de usuario")
    @Email(message = "El nombre de usuario es incorrecto, favor de proporcionar un correo electrónico")
    private String username;
    @NotBlank(message = "Favor de proporcionar la contraseña")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
