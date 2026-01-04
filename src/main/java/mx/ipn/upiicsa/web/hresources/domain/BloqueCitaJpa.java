package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;

@Data
@Entity
@Table(name = "tce07_bloque_cita")
@IdClass(BloqueCitaId.class)
public class BloqueCitaJpa {
    @Id
    @Column(name = "fk_id_sucursal")
    private Integer idSucursal;

    @ManyToOne
    @JoinColumn(name = "fk_id_sucursal", insertable = false, updatable = false)
    private SucursalJpa sucursal;

    @Column(name = "fk_id_cita")
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "fk_id_cita", insertable = false, updatable = false)
    private CitaJpa cita;

    @Id
    @Column(name = "fh_inicio")
    private LocalDateTime fechaInicio;

    @Id
    @Column(name = "fh_fin")
    private LocalDateTime fechaFin;
}
