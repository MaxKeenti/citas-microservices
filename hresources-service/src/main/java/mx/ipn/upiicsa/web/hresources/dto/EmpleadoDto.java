package mx.ipn.upiicsa.web.hresources.dto;

public class EmpleadoDto {
    public Integer id;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public SucursalDto sucursal;
    public Boolean activo;

    public EmpleadoDto(Integer id, String nombre, String primerApellido, String segundoApellido, SucursalDto sucursal) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.sucursal = sucursal;
        this.activo = true;
    }
}
