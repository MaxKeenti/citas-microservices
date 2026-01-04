package mx.ipn.upiicsa.web.accesscontrol.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Persona {
    private Integer id;
    private Integer idGenero;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private Usuario usuario;
}
