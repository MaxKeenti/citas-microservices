package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tce07_bloque_cita")
@IdClass(BloqueCitaId.class)
public class BloqueCita extends PanacheEntityBase {
    @Id
    @Column(name = "fk_id_sucursal")
    public Integer idSucursal;

    @ManyToOne
    @JoinColumn(name = "fk_id_sucursal", insertable = false, updatable = false)
    public Sucursal sucursal;

    @Column(name = "fk_id_cita")
    public Integer idCita; // Cross-service FK to Appointment

    @Id
    @Column(name = "fh_inicio")
    public LocalDateTime fechaInicio;

    @Id
    @Column(name = "fh_fin")
    public LocalDateTime fechaFin;
}

class BloqueCitaId implements Serializable {
    public Integer idSucursal;
    public LocalDateTime fechaInicio;
    public LocalDateTime fechaFin;

    // equals/hashCode required
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BloqueCitaId that = (BloqueCitaId) o;
        return Objects.equals(idSucursal, that.idSucursal) &&
                Objects.equals(fechaInicio, that.fechaInicio) &&
                Objects.equals(fechaFin, that.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSucursal, fechaInicio, fechaFin);
    }
}
