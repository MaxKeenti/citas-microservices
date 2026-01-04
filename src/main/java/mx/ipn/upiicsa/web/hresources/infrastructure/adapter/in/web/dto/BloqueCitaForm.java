package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BloqueCitaForm {
    @NotNull(message = "La sucursal es requerida")
    private Integer idSucursal;
    private Integer idCita;
    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDateTime fechaInicio;
    @NotNull(message = "La fecha de fin es requerida")
    private LocalDateTime fechaFin;
}
