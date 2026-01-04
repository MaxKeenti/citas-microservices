package mx.ipn.upiicsa.web.portal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Servicio {
    public Integer id;
    public String nombre;
    public String descripcion;
    public Integer duracion;
    public Integer activo;
}
