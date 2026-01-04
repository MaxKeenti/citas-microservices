package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpleadoForm {
    private Integer idEmpleado; // Will be persona id
    @NotNull(message = "Seleccione la persona")
    private Integer idPersona;
    @NotNull(message = "Seleccione la sucursal")
    private Integer idSucursal;
}
