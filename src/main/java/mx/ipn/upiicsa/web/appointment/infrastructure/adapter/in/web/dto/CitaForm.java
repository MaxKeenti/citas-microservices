package mx.ipn.upiicsa.web.appointment.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaForm {
    private Integer id;
    @NotNull(message = "Seleccione la persona")
    private Integer idPersona;
    @NotNull(message = "Seleccione el servicio")
    private Integer idServicio;
    @NotNull(message = "Seleccione la lista de precio")
    private Integer idListaPrecio;
    @NotNull(message = "Seleccione la sucursal")
    private Integer idSucursal;
    @NotNull(message = "Seleccione el empleado")
    private Integer idEmpleado;
    private LocalDateTime fechaHora;
    private Integer customDuration;
}
