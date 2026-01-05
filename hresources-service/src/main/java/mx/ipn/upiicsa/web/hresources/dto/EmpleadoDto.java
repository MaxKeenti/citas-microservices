package mx.ipn.upiicsa.web.hresources.dto;

import mx.ipn.upiicsa.web.hresources.model.Sucursal;

public class EmpleadoDto {
    public Integer id;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public Sucursal sucursal;
    public Boolean activo;

    public EmpleadoDto(Integer id, String nombre, String primerApellido, String segundoApellido, Sucursal sucursal) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.sucursal = sucursal;
        this.activo = true;
    }
}
