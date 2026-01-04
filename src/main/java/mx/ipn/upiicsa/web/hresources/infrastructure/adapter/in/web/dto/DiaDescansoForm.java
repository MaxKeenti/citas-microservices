package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DiaDescansoForm {
    private Integer id;
    @NotNull(message = "El empleado es requerido")
    private Integer idEmpleado;
    @NotNull(message = "La fecha de descanso es requerida")
    private LocalDate fechaDescanso;
}
