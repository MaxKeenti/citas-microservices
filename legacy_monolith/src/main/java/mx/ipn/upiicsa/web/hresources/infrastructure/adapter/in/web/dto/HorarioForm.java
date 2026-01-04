package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Data
public class HorarioForm {
    private Integer id;
    @NotNull(message = "La sucursal es requerida")
    private Integer idSucursal;
    @NotNull(message = "El día laboral es requerido")
    private Integer idDia;
    @NotNull(message = "La hora de inicio es requerida")
    private LocalTime horaInicio;
    @NotNull(message = "La hora de fin es requerida")
    private LocalTime horaFin;
}
