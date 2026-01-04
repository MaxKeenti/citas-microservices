package mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstadoListaPrecioForm {
    private Integer id;
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
}
