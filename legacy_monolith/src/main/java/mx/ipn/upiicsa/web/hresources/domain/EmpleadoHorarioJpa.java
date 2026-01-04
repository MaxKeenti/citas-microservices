package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tce06_empleado_horario")
@IdClass(EmpleadoHorarioId.class)
public class EmpleadoHorarioJpa {
    @Id
    @Column(name = "fk_id_horario")
    private Integer idHorario;

    @Id
    @Column(name = "fk_id_persona")
    private Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "fk_id_horario", insertable = false, updatable = false)
    private HorarioJpa horario;

    @ManyToOne
    @JoinColumn(name = "fk_id_persona", insertable = false, updatable = false)
    private EmpleadoJpa empleado;
}
