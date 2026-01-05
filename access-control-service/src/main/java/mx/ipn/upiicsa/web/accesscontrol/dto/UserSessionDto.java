package mx.ipn.upiicsa.web.accesscontrol.dto;

import java.util.List;
import java.time.LocalDate;

public class UserSessionDto {
    public Integer id;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public LocalDate fechaNacimiento;
    public Integer idGenero;
    public List<String> roles;

    public UserSessionDto(Integer id, String nombre, String primerApellido, String segundoApellido,
            LocalDate fechaNacimiento, Integer idGenero, List<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.idGenero = idGenero;
        this.roles = roles;
    }
}
