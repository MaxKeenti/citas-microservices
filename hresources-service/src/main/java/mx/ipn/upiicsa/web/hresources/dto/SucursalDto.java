package mx.ipn.upiicsa.web.hresources.dto;

public class SucursalDto {
    public Integer id;
    public String nombre;

    public Double lat;
    public Double lng;
    public Integer idEstablecimiento;

    public SucursalDto() {
    }

    public SucursalDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public SucursalDto(Integer id, String nombre, Double lat, Double lng, Integer idEstablecimiento) {
        this.id = id;
        this.nombre = nombre;
        this.lat = lat;
        this.lng = lng;
        this.idEstablecimiento = idEstablecimiento;
    }
}
