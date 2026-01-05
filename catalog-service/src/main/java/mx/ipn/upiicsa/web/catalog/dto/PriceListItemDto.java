package mx.ipn.upiicsa.web.catalog.dto;

public class PriceListItemDto {
    public Integer id; // ServicioListaPrecio ID
    public Integer idServicio;
    public String nombreServicio;
    public Integer precio;

    public PriceListItemDto() {
    }

    public PriceListItemDto(Integer id, Integer idServicio, String nombreServicio, Integer precio) {
        this.id = id;
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precio = precio;
    }
}
