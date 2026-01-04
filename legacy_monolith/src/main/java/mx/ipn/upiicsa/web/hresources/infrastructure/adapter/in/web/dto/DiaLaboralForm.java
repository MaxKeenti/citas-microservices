package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiaLaboralForm {
    private Integer id;
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    private String descripcion;
    private Integer activo;
}
