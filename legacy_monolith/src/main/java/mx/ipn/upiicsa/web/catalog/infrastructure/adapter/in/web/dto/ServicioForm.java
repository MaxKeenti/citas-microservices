package mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServicioForm {
    private Integer id;
    @NotBlank(message = "Favor de proporcionar el nombre del servicio")
    private String nombre;
    private String descripcion;
    @NotNull(message = "Favor de proporcionar la duración en minutos")
    private Integer duracion;
    private Boolean activo = true;
}
