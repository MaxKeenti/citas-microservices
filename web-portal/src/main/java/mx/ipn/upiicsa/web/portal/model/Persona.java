package mx.ipn.upiicsa.web.portal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Persona {
    public Integer id;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
}
