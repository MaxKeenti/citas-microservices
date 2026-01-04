package mx.ipn.upiicsa.web.hresources.domain;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BloqueCitaId implements Serializable {
    private Integer idSucursal;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
