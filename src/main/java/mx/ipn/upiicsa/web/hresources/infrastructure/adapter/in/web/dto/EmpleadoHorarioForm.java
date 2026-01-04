package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpleadoHorarioForm {
    @NotNull(message = "El horario es requerido")
    private Integer idHorario;
    @NotNull(message = "El empleado es requerido")
    private Integer idEmpleado;
}
