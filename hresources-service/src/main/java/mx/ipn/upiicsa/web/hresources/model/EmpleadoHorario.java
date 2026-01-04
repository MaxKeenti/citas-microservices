package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tce09_empleado_horario")
@IdClass(EmpleadoHorarioId.class)
public class EmpleadoHorario extends PanacheEntityBase {
    @Id
    @Column(name = "fk_id_empleado")
    public Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "fk_id_empleado", insertable = false, updatable = false)
    public Empleado empleado;

    @Id
    @Column(name = "fk_id_horario")
    public Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "fk_id_horario", insertable = false, updatable = false)
    public Horario horario;
}

class EmpleadoHorarioId implements Serializable {
    public Integer idEmpleado;
    public Integer idHorario;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmpleadoHorarioId that = (EmpleadoHorarioId) o;
        return Objects.equals(idEmpleado, that.idEmpleado) && Objects.equals(idHorario, that.idHorario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, idHorario);
    }
}
