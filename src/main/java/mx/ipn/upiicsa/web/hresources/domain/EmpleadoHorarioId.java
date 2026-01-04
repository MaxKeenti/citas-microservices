package mx.ipn.upiicsa.web.hresources.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class EmpleadoHorarioId implements Serializable {
    private Integer idHorario;
    private Integer idEmpleado;
}
