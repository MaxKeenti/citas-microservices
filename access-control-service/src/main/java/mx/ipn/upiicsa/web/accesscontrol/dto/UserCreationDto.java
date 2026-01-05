package mx.ipn.upiicsa.web.accesscontrol.dto;

import java.time.LocalDate;

public class UserCreationDto {
    public String username; // login/email
    public String password;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public LocalDate fechaNacimiento;
    public Integer idGenero;
    public Integer idRol; // 1=Admin, 2=Employee, 3=Client
}
