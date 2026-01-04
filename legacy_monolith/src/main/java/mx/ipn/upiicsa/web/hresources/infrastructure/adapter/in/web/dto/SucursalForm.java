package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SucursalForm {
    private Integer id;
    @NotNull(message = "Seleccione un establecimiento")
    private Integer idEstablecimiento;
    @NotBlank(message = "Favor de proporcionar el nombre de la sucursal")
    private String nombre;
    @NotNull(message = "Favor de seleccionar la ubicación en el mapa")
    private Double latitud;
    @NotNull(message = "Favor de seleccionar la ubicación en el mapa")
    private Double longitud;
}
