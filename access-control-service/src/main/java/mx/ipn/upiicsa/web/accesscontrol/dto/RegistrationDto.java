package mx.ipn.upiicsa.web.accesscontrol.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RegistrationDto {
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public LocalDate fechaNacimiento;
    public Integer idGenero;

    public String username; // login
    public String password;
    public String confirmPassword;
}
